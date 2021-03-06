package com.imall.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imall.api.CategoryApi;
import com.imall.pojo.Category;
import com.imall.response.ImallResult;
import com.imall.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	/*
	 * url:http://api.imall.com/api/item/category/list?pid=0
	 */
	@GetMapping("list")
	public ResponseEntity categoryList(@RequestParam("pid") Long pid ) {
		log.info("***pid***: {}", pid);
		List<Category> categories = categoryService.queryCategoriesByPid(pid);
		if (CollectionUtils.isEmpty(categories)) {
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.ok(categories);
	}

	@RequestMapping("names")
	public ResponseEntity queryNameByIds(@RequestParam("ids")List<Long> ids) {
		log.info("***ids:***:{}", ids);
		List<Category> categories = categoryService.queryNameByIds(ids);
		List<String> categoryNames = categories.stream().map(Category :: getName).collect(Collectors.toList());
		return ResponseEntity.ok(categoryNames);
	}
	@GetMapping("list/ids")
	public ResponseEntity queryCategoriesByIds(@RequestParam("ids") List<Long> ids){
		List<Category> categories = categoryService.queryCategoriesByCids(ids);
		if (CollectionUtils.isEmpty(categories)) {
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.ok(categories);
	}
	
}
