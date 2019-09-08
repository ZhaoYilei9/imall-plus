package com.imall.service;

import java.util.List;

import com.imall.common.pojo.PageResult;
import com.imall.common.vo.BrandVO;
import com.imall.pojo.Brand;
import com.imall.pojo.Category;

public interface BrandService {

	public PageResult<Brand> brandList(String key, Integer page, Integer rows, String sortBy, Boolean desc);

	public Integer saveBrand(BrandVO brand);

	public List<Category> queryCategoriesByBid(Long id);

	public Integer updateBrand(BrandVO brand);

	public Integer deleteBrandById(Long bid);

	public List<Brand> getBrandsByCid(Long cid);

	public List<Brand> getBrandsByBids(List<Long> ids);

}
