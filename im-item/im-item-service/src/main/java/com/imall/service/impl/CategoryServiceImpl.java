package com.imall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
