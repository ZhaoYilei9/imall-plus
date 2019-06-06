package com.imall.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imall.common.pojo.PageResult;
import com.imall.common.vo.BrandVO;
import com.imall.mapper.BrandMapper;
import com.imall.mapper.CategoryBrandMapper;
import com.imall.pojo.Brand;
import com.imall.pojo.Category;
import com.imall.pojo.CategoryBrand;
import com.imall.service.BrandService;
import com.imall.service.CategoryService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;

	@Autowired
	private CategoryBrandMapper categoryBrandMapper;
	
	@Autowired 
	private CategoryService categoryService;
	@Override
	public PageResult<Brand> brandList(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
		// 1.开启分页助手
		PageHelper.startPage(page, rows);
		// 2.排序
		Example example = new Example(Brand.class);
		example.setOrderByClause(sortBy + (desc ? " desc" : " asc"));
		Criteria criteria = example.createCriteria();
		// 3.根据key进行查询
		if (StringUtils.isNotBlank(key)) {
			criteria.andLike("name", key + "%").orEqualTo("letter", key.toUpperCase());
		}
		List<Brand> brands = brandMapper.selectByExample(example);
		PageInfo<Brand> pageInfo = new PageInfo<>(brands);
		PageResult<Brand> result = new PageResult<>();
		result.setItems(pageInfo.getList());
		result.setPage(pageInfo.getPageNum());
		result.setTotal(pageInfo.getTotal());
		result.setPages(pageInfo.getPages());

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveBrand(BrandVO brand) {
		Brand brandDb = new Brand();
		BeanUtils.copyProperties(brand, brandDb);
		// 1.保存品牌信息，即新增商品品牌表
		int insertBrandCounts = brandMapper.insert(brandDb);
		log.info("***新增品牌id:{}", brandDb.getId());
		// 2.保存品牌分类信息，即新增品牌商品分类表
		int insertCBCounts = 0;
		List<Long> cids = brand.getCids();
		for (Long cid : cids) {
			CategoryBrand cb = new CategoryBrand();
			cb.setBrandId(brandDb.getId());
			cb.setCategoryId(cid);
			insertCBCounts = categoryBrandMapper.insert(cb);
		}
		if (insertBrandCounts == 0 || insertCBCounts == 0) {
			return 0;
		}
		return 1;
	}

	@Override
	public List<Category> queryCategoriesByBid(Long id) {
		Example example = new Example(CategoryBrand.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("brandId", id);
		List<CategoryBrand> categoryBrands = categoryBrandMapper.selectByExample(example);
		List<Long> cids = categoryBrands.stream().map(CategoryBrand :: getCategoryId).collect(Collectors.toList());
		log.info("***cids:{}", cids);
		List<Category> categories = new ArrayList<Category>();
		for (Long cid : cids) {
			Category category = categoryService.queryCategoryByCid(cid);
			categories.add(category);
		}
		return categories;
	}

	@Override
	public Integer updateBrand(BrandVO brand) {
		
		return null;
	}
	
}
