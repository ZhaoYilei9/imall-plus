package com.imall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imall.common.pojo.PageResult;
import com.imall.pojo.Brand;
import com.imall.response.ImallResult;
import com.imall.service.BrandService;

/**
 * 
 * @author zyl brand/page?key=&page=1&rows=5&sortBy=id&desc=false
 */
@RestController
@RequestMapping("brand")
public class BrandController {
	
	@Autowired
	private BrandService brandService;

	@RequestMapping("page")
	public  PageResult<Brand> brandList (@RequestParam(name = "key",required = false) String key, 
										  @RequestParam(name = "page", defaultValue = "1") Integer page,
										  @RequestParam(name = "rows", defaultValue = "5") Integer rows,
										  @RequestParam(name = "sortBy") String sortBy,
										  @RequestParam(name = "desc") Boolean desc) {
		PageResult<Brand> brands = brandService.brandList(key,page,rows,sortBy,desc);
		
		return brands;
	}

}
