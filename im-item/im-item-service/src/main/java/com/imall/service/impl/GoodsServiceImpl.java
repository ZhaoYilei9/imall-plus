package com.imall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.imall.common.dto.CartDto;
import com.imall.enums.ExceptionEnum;
import com.imall.exception.ImException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.ListenerContainerFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imall.common.pojo.PageResult;
import com.imall.common.vo.GoodsVO;
import com.imall.common.vo.SkuVO;
import com.imall.common.vo.SpuDetailVO;
import com.imall.mapper.SkuMapper;
import com.imall.mapper.SpuDetailMapper;
import com.imall.mapper.SpuMapper;
import com.imall.mapper.StockMapper;
import com.imall.pojo.Brand;
import com.imall.pojo.Sku;
import com.imall.pojo.Spu;
import com.imall.pojo.SpuDetail;
import com.imall.pojo.Stock;
import com.imall.service.GoodsService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private SpuMapper spuMapper;
	
	@Autowired
	private SpuDetailMapper spuDetailMapper;
	
	@Autowired
	private SkuMapper skuMapper;

	@Autowired
	private StockMapper stockMapper;

	@Autowired
	private AmqpTemplate amqpTemplate;
	@Override
	public PageResult<Spu> spuList(String key, Boolean saleable, Integer page, Integer rows) {
		// 1.开启分页助手
		PageHelper.startPage(page, rows);
		Example example = new Example(Spu.class);
		Criteria criteria = example.createCriteria();
		//2.根据是否上架查询
		criteria.andEqualTo("saleable", saleable);
		// 3.根据key进行查询
		if (StringUtils.isNotBlank(key)) {
			criteria.andLike("title", "%" + key + "%");
		}
		List<Spu> spus = spuMapper.selectByExample(example);
		PageInfo<Spu> pageInfo = new PageInfo<>(spus);
		PageResult<Spu> result = new PageResult<>();
		result.setItems(pageInfo.getList());
		result.setPage(pageInfo.getPageNum());
		result.setTotal(pageInfo.getTotal());
		result.setPages(pageInfo.getPages());

		log.info("***商品查询服务层pageResult:{}", result);
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveGoods(GoodsVO goodsVO) {
		Spu spu = new Spu();
		SpuDetail spuDetail = new SpuDetail();
		SpuDetailVO spuDetailVO = goodsVO.getSpuDetail();
		List<SkuVO> skus = goodsVO.getSkus();
		BeanUtils.copyProperties(goodsVO, spu);
		spu.setCreateTime(new Date());
		spu.setLastUpdateTime(spu.getCreateTime());
		spuMapper.insertSelective(spu);
		
		BeanUtils.copyProperties(spuDetailVO, spuDetail);
		spuDetail.setSpuId(spu.getId());
		spuDetailMapper.insertSelective(spuDetail);
		log.info("***商品新增-spu:{}", spu);
		log.info("***商品新增-spuDetail:{}", spuDetail);
		for (SkuVO skuVO : skus) {
			log.info("***商品新增-sku:{}", skuVO);
			Sku sku = new Sku();
			BeanUtils.copyProperties(skuVO, sku);
			sku.setSpuId(spu.getId());
			sku.setCreateTime(new Date());
			sku.setLastUpdateTime(sku.getCreateTime());
			skuMapper.insertSelective(sku);
			Stock stock = new Stock();
			stock.setSkuId(sku.getId());
			stock.setStock(skuVO.getStock());
			log.info("***商品新增-sku-stock:{}", skuVO.getStock());
			stockMapper.insertSelective(stock);
		}
		this.sendMessage(spu.getId(), "insert");
	}

	private void sendMessage(Long id, String type) {
		 // 发送消息
		try {
			this.amqpTemplate.convertAndSend("item." + type, id);
		} catch (Exception e) {
			log.error("{}商品消息发送异常，商品id：{}", type, id, e);
		}
	}

	@Override
	public SpuDetail querySpuDetail(Long spuId) {
		SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
		return spuDetail;
	}

	@Override
	public List<Sku> querySkuListBySpuId(Long id) {
		Example example = new Example(Sku.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("spuId",id);
		List<Sku> skuList = skuMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(skuList)) {
			return null;
		}
		return skuList;
	}

	@Override
	public Spu querySpuById(Long id) {

		//根据spuId查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);

        //查询spuDetail
        SpuDetail detail = querySpuDetail(id);

        //查询skus
        List<Sku> skus = querySkuListBySpuId(id);

        spu.setSpuDetail(detail);
        spu.setSkus(skus);

		return spu;
	}

	@Override
	public List<Sku> querySkuByIds(List<Long> ids) {
		List<Sku> skus = skuMapper.selectByIdList(ids);
		if (CollectionUtils.isEmpty(skus)){
			throw new ImException(ExceptionEnum.SKU_NOT_FOUND);
		}
		fillStock(ids, skus);
		return skus;
	}

	private void fillStock(List<Long> ids, List<Sku> skus) {
		List<Stock> stocks = stockMapper.selectByIdList(ids);
		//s为stocks的遍历对象
		Map<Long, Long> stockMap = stocks.stream().collect(Collectors.toMap(s -> s.getSkuId(), s -> s.getStock()));
		for (Sku sku : skus) {
			sku.setStock(stockMap.get(sku.getId()));
		}

	}

	@Transactional
	@Override
	public void decreaseStock(List<CartDto> carts) {
		for (CartDto cart : carts) {
			stockMapper.decreaseStock(cart.getSkuId(),cart.getNum());
		}
	}
}
