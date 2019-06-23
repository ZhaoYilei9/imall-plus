package com.imall.api;

import java.util.List;

import org.apache.log4j.varia.FallbackErrorHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imall.bo.SpuBo;
import com.imall.common.pojo.PageResult;
import com.imall.common.vo.GoodsVO;import com.imall.pojo.Sku;
import com.imall.pojo.Spu;
import com.imall.response.ImallResult;


public interface GoodsApi {
	
	@GetMapping("spu/page")
    PageResult<Spu> spuList(@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "saleable", required = false) Boolean saleable, 
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "5") Integer rows);
	
	@PostMapping("goods")
	public ImallResult saveGoods(@RequestBody GoodsVO goodsVO);
	
	@GetMapping("spu/detail/{spuId}")
	public ResponseEntity querySpuDetail(@PathVariable("spuId") Long spuId);
	
	@GetMapping("sku/list")
	public ImallResult<List<Sku>> querySkuList(@RequestParam("id") Long id);
	
}
