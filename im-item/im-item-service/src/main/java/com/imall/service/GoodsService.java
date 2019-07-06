package com.imall.service;

import java.util.List;

import com.imall.bo.SpuBo;
import com.imall.common.pojo.PageResult;
import com.imall.common.vo.GoodsVO;
import com.imall.pojo.Sku;
import com.imall.pojo.Spu;
import com.imall.pojo.SpuDetail;

public interface GoodsService {

	PageResult<Spu> spuList(String key, Boolean saleable, Integer page, Integer rows);

	void saveGoods(GoodsVO goodsVO);

	SpuDetail querySpuDetail(Long spuId);

	List<Sku> querySkuListBySpuId(Long id);


}
