package com.imall.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.imall.client.CategoryClient;
import com.imall.client.GoodsClient;
import com.imall.client.SpecificationClient;
import com.imall.common.utils.JsonUtils;
import com.imall.pojo.Goods;
import com.imall.pojo.Sku;
import com.imall.pojo.SpecParam;
import com.imall.pojo.Spu;
import com.imall.pojo.SpuDetail;

@Service
public class IndexService {
	@Autowired
    private GoodsClient goodsClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpecificationClient specificationClient;
    
    //构造商品存入索引库
    public Goods buildGoods(Spu spu) {
    	//1.准备数据
    	Long id = spu.getId();
    	List<Sku> skus = goodsClient.querySkuList(id).getData();
    	List<Long> prices = new ArrayList<Long>();
    	List<Map<String, Object>> skuList = new ArrayList<>();
    	for (Sku sku : skus) {
			prices.add(sku.getPrice());
			Map<String, Object> map = new HashMap<>();
			map.put("id", sku.getId());
			map.put("title", sku.getTitle());
			map.put("image", StringUtils.isBlank(sku.getImages()) ? "" : sku.getImages().split(",")[0]);
			map.put("price", sku.getPrice());
			skuList.add(map);
		}
    	SpuDetail spuDetail = (SpuDetail) goodsClient.querySpuDetail(id).getBody();
    	List<String> names = (List<String>)categoryClient.queryNameByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3())).getData();
    	List<SpecParam> params = (List<SpecParam>) this.specificationClient.getSpecParamsByCid(spu.getCid3()).getData();
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
}
