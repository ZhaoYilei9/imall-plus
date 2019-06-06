package com.imall.service;

import java.util.List;

import com.imall.common.pojo.PageResult;
import com.imall.pojo.Brand;

public interface BrandService {

	public PageResult<Brand> brandList(String key, Integer page, Integer rows, String sortBy, Boolean desc);

}
