package com.imall.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.imall.mapper.CategoryMapper;
import com.imall.pojo.Category;
import com.imall.service.CategoryService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryMapper categoryMapper;
	@Override
	public List<Category> queryCategoriesByPid(Long pid) {
		Example example = new Example(Category.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", pid);
		List<Category> categories = categoryMapper.selectByExample(example);
		return categories;
		
	}
	@Override
	public Category queryCategoryByCid(Long cid) {
		Category category = categoryMapper.selectByPrimaryKey(cid);
		return category;
	}
	@Override
	public List<Category> queryNameByIds(List<Long> ids) {

		List<Category> categories = categoryMapper.selectByIdList(ids);
		if (CollectionUtils.isEmpty(categories)) {
			return null;
		}
		return categories;
	}
	@Override
	public List<Category> queryCategoriesByCids(List<Long> ids) {
		List<Category> categories = categoryMapper.selectByIdList(ids);
		if (CollectionUtils.isEmpty(categories)) {
			return null;
		}
		return categories;
	}
	

}
