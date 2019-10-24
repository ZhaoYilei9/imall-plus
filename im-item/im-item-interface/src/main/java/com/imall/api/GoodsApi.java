package com.imall.api;

import java.util.List;

import com.imall.common.dto.CartDto;
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
import com.imall.pojo.SpuDetail;
import com.imall.response.ImallResult;


public interface GoodsApi {
	
	@GetMapping("spu/page")
    PageResult<Spu> spuList(@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "saleable", required = false) Boolean saleable, 
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "5") Integer rows);
	
	@PostMapping("goods")
	public void saveGoods(@RequestBody GoodsVO goodsVO);
	
	@GetMapping("spu/detail/{spuId}")
	public SpuDetail querySpuDetail(@PathVariable("spuId") Long spuId);
	
	@GetMapping("sku/list")
	public List<Sku> querySkuList(@RequestParam("id") Long id);

	@GetMapping("spu/{id}")
	public Spu querySpuById(@PathVariable("id") Long id);

	@GetMapping ("sku/list/ids")
	public List<Sku> querySkuByIds(@RequestParam("ids") List<Long> ids);

	@PostMapping("stock/decrease")
	void decreaseStock(@RequestBody List<CartDto> cartDTOS);
}
