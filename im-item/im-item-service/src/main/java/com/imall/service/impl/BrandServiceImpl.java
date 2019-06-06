package com.imall.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imall.common.pojo.PageResult;
import com.imall.mapper.BrandMapper;
import com.imall.pojo.Brand;
import com.imall.service.BrandService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandMapper brandMapper;
	@Override
	public PageResult<Brand> brandList(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
		//1.开启分页助手
		PageHelper.startPage(page, rows);
		//2.排序
		Example example = new Example(Brand.class);
		example.setOrderByClause(sortBy + (desc ? " desc" : " asc"));
		Criteria criteria = example.createCriteria();
		//3.根据key进行查询
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

}
