package com.imall.service;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.lucene.index.Term;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.imall.client.BrandClient;
import com.imall.client.CategoryClient;
import com.imall.client.GoodsClient;
import com.imall.client.SpecificationClient;
import com.imall.common.pojo.PageResult;
import com.imall.common.utils.JsonUtils;
import com.imall.enums.ExceptionEnum;
import com.imall.exception.ImException;
import com.imall.pojo.Brand;
import com.imall.pojo.Category;
import com.imall.pojo.Goods;
import com.imall.pojo.Sku;
import com.imall.pojo.SpecParam;
import com.imall.pojo.Spu;
import com.imall.pojo.SpuDetail;
import com.imall.repo.GoodsRepository;
import com.imall.vo.SearchRequest;
import com.imall.vo.SearchResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IndexService {
	@Autowired
    private GoodsClient goodsClient;

    @Autowired
    private CategoryClient categoryClient;
    
    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecificationClient specificationClient;
    
    @Autowired
    private GoodsRepository goodsRepository;
    
    @Autowired
    private ElasticsearchTemplate template;
    
    //构造商品存入索引库
    public Goods buildGoods(Spu spu) throws ImException{
    	//1.准备数据
    	Long id = spu.getId();
    	
    	List<Sku> skus = goodsClient.querySkuList(id);
    	log.info("*****skus:{}",skus);
    	if (CollectionUtils.isEmpty(skus)) {
			throw new ImException(ExceptionEnum.SKU_NOT_FOUND);
		}
    	List<Long> prices = new ArrayList<Long>();
    
    	List<Map<String, Object>> skuList = new ArrayList<>();
    	
    	for (Sku sku : skus) {
			prices.add(sku.getPrice());
			Map<String, Object> map = new HashMap<>();
			map.put("id", sku.getId());
			map.put("title", sku.getTitle());
			map.put("image",  StringUtils.substringBefore(sku.getImages(), ","));
			map.put("price", sku.getPrice());
			skuList.add(map);
		}
    	if (CollectionUtils.isEmpty(skuList)) {
			throw new ImException(ExceptionEnum.SKU_NOT_FOUND);
		}
    	log.info("*****skuList:{}",skuList);
    	SpuDetail spuDetail = (SpuDetail) goodsClient.querySpuDetail(id);
    	List<String> names = (List<String>)categoryClient.queryNameByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
    	List<SpecParam> params = (List<SpecParam>) this.specificationClient.getSpecParamsByCid(spu.getCid3());
    	Map<Long, String> genericSpec  = JsonUtils.parseMap(spuDetail.getGenericSpec(), Long.class, String.class);
    	Map<Long, List<String>> specialSpec = JsonUtils.nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {});
    	Map<String,Object> specMap = new HashMap<>();
    	for (SpecParam param : params) {
			if (param.getGeneric()) {
				String value = genericSpec.get(param.getId());
				
				if (param.getNumeric()) {
					value = this.chooseSegment(value, param);
				}
				specMap.put(param.getName(), value);
			}else {
				specMap.put(param.getName(), specialSpec.get(param.getId()));
			}
		}
    	Goods goods = new Goods();
    	goods.setCid1(spu.getCid1());
    	goods.setCid2(spu.getCid2());
    	goods.setCid3(spu.getCid3());
    	goods.setId(spu.getId());
    	goods.setBrandId(spu.getBrandId());
    	goods.setPrice(prices);
    	goods.setSkus(JsonUtils.serialize(skuList));
    	goods.setCreateTime(spu.getCreateTime());
    	goods.setAll(spu.getTitle() + " " + StringUtils.join(names," "));
    	goods.setSubTitle(spu.getSubTitle());
    	goods.setSpecs(specMap);
    	return goods;
    	
    }
    
 

	private String chooseSegment(String value, SpecParam param) {
		double val = NumberUtils.toDouble(value);
		String result = "其他";
		for (String segment : param.getSegments().split(",")) {
			String[] segs = segment.split("-");
			double begin = NumberUtils.toDouble(segs[0]);
			double end = Double.MAX_VALUE;
			if (segs.length == 2) {
				end = NumberUtils.toDouble(segs[1]);
			}
			if (val >= begin && val < end) {
				if (segs.length == 1) {
					result = segs[0] + param.getUnit() + "以上";
				}else if (begin == 0) {
					result = end + param.getUnit() + "以下";
				}else {
					result = segment + param.getUnit();
				}
				
				break;
			}
			
			
		}
		return result;
	}



	public SearchResult<Goods> search(SearchRequest request) {
		String key = request.getKey();
		log.info("*****key:{}", key);
		if (StringUtils.isBlank(key)) {
			return null;	
		}
		Integer page = request.getPage() - 1;
		Integer size = request.getSize();
		//1.创建查询构建器
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		//2.查询
		//2.1 对查询到的结果进行筛选
		queryBuilder.withSourceFilter(new FetchSourceFilter(new String[] {
				"id", "skus", "subTitle"
		}, null));
		
		//2.2 根据分类/品牌聚合
		//2.2.1根据搜索条件构建基本查询
		QueryBuilder basicQuery = buildBasicQuery(request);
		queryBuilder.withQuery(basicQuery);
		//2.3.1 根据分类聚合
		String categoryAggName = "category_agg";
		queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
		//2.3.2 根据品牌聚合
		String brandAggName = "brand_agg";
		queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));
		//2.4 分页
		queryBuilder.withPageable(PageRequest.of(page, size));
		//2.5 返回结果
//		Page<Goods> result = goodsRepository.search(queryBuilder.build());
		AggregatedPage<Goods> result = template.queryForPage(queryBuilder.build(), Goods.class);
		//2.6 解析结果
		long total = result.getTotalElements();
		int totalPages = result.getTotalPages();
		List<Goods> items = result.getContent();
		Aggregations agg = result.getAggregations();
		List<Category> categories = parseCategoryAgg(agg.get(categoryAggName));
		List<Brand> brands = parseBrandAgg(agg.get(brandAggName));
		//3.对规格参数进行聚合
		List<Map<String, Object>> specs = new ArrayList<>();
		if (!CollectionUtils.isEmpty(categories) && categories.size() == 1) {
			
			List<SpecParam> params = specificationClient.getSpecParamsByCid(categories.get(0).getId());
			if (!CollectionUtils.isEmpty(params)) {
				
				for (SpecParam param : params) {
					//3.1在原有搜索条件的基础上进行查询

					queryBuilder.addAggregation(AggregationBuilders.terms(param.getName())
							.field("specs."+param.getName()+".keyword"));
				}
				AggregatedPage<Goods> specResult = template.queryForPage(queryBuilder.build(), Goods.class);
				Aggregations aggregations = specResult.getAggregations();
				for (SpecParam param : params) {
					Map<String, Object> map = new HashMap<>();
					Terms terms = aggregations.get(param.getName());
					map.put("k", param.getName());
					map.put("options", terms.getBuckets().stream().map(b -> b.getKeyAsString())
							.collect(Collectors.toList()));
					specs.add(map);
				}
			}
		}
		
		return new SearchResult<>(total, totalPages, items, brands, categories, specs);
	}





	private QueryBuilder buildBasicQuery(SearchRequest request) {
		//1.创建布尔查询（查询条件和过滤条件）
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		//2.创建查询条件
		queryBuilder.must(QueryBuilders.matchQuery("all", request.getKey()));
		//3.创建过滤条件
		Map<String, String> filter = request.getFilter();
		for (Map.Entry<String, String> entry : filter.entrySet()) {
			if (!entry.getKey().equals("cid3") && !entry.getKey().equals("brandId")) {
				queryBuilder.filter(QueryBuilders.termQuery("specs."+entry.getKey()+".keyword", entry.getValue()));
			}else {
				queryBuilder.filter(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
			}
		}
		return queryBuilder;
	}



	private List<Category> parseCategoryAgg(LongTerms terms) {
		try {
			List<Long> cids = terms.getBuckets().stream().map(p -> p.getKeyAsNumber().longValue()).collect(Collectors.toList());
			List<Category> categories = this.categoryClient.queryCategoriesByIds(cids);
			return categories;
		} catch (Exception e) {
			log.error("*****[搜索服务]-查询分类失败***:{}",e.getMessage());
			return null;
		}
	}
	private List<Brand> parseBrandAgg(LongTerms terms) {
		try {
			List<Long> ids = terms.getBuckets().stream().map(p -> p.getKeyAsNumber().longValue()).collect(Collectors.toList());
			List<Brand> brands = this.brandClient.queryBrandsByIds(ids);
			return brands;
		} catch (Exception e) {
			log.error("*****[搜索服务]-查询品牌失败***:{}",e.getMessage());
			return null;
		}
	}
}
