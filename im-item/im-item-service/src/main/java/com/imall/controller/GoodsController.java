package com.imall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imall.common.pojo.PageResult;
import com.imall.pojo.Spu;
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
	
}
