package com.imall.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imall.pojo.Brand;

@RequestMapping("brand")
public interface BrandApi {
	@GetMapping("list/ids")
    List<Brand> queryBrandsByIds(@RequestParam("ids") List<Long> ids);
}
