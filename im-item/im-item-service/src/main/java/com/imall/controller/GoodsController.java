package com.imall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imall.common.pojo.PageResult;
import com.imall.common.vo.GoodsVO;
import com.imall.pojo.Spu;
import com.imall.pojo.SpuDetail;
import com.imall.response.ImallResult;
import com.imall.service.GoodsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	@RequestMapping("spu/page")
	public PageResult<Spu> spuList(@RequestParam("key") String key,
			@RequestParam("saleable") Boolean saleable, 
			@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows){
		log.info("***商品查询:{},{},{},{}",page,rows,key,saleable);
		PageResult<Spu> result = goodsService.spuList(key,saleable,page,rows);
		return result;
	}
	
	/**
	 * http://api.imall.com/api/item/goods
	 */
	@PostMapping("goods")
	public ImallResult saveGoods(@RequestBody GoodsVO goodsVO) {
		log.info("***goodsVO:{}", goodsVO);
		goodsService.saveGoods(goodsVO);
		return null;
	}
	/**
	 * http://api.imall.com/api/item/spu/detail/33
	 */
	@GetMapping("spu/detail/{spuId}")
	public ResponseEntity querySpuDetail(@PathVariable("spuId") Long spuId) {
		log.info("***商品修改-查询商品detail-spuId:{}",spuId);
		SpuDetail spuDetail = goodsService.querySpuDetail(spuId);
		if (spuDetail == null) {
			return ResponseEntity.status(500).build();
		}
		return ResponseEntity.ok(spuDetail);
	}
	/**
	 * http://api.imall.com/api/item/sku/list?id=5
	 */
	@GetMapping("sku/list")
	public ImallResult querySkuList(@RequestParam("id") Long id) {
		log.info("***商品修改-查询商品skuList-spuId:{}",id);
		return null;
	}
}
