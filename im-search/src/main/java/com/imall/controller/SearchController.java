package com.imall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imall.common.pojo.PageResult;
import com.imall.pojo.Goods;
import com.imall.service.IndexService;
import com.imall.vo.SearchRequest;
import com.imall.vo.SearchResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@Slf4j
public class SearchController {

	@Autowired
	private IndexService indexService;

	/**
	 * 搜索商品
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("page")
	public ResponseEntity<SearchResult<Goods>> search(@RequestBody SearchRequest request) {
		log.info("*****request:{}", request.getKey());
		SearchResult<Goods> result = this.indexService.search(request);
		if (result == null) {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		return ResponseEntity.ok(result);
	}
	
}
