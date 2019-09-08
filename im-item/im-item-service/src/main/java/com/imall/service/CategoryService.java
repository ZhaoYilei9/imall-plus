package com.imall.service;

import java.util.List;

import com.imall.pojo.Category;

public interface CategoryService {

	List<Category> queryCategoriesByPid(Long pid);

	Category queryCategoryByCid(Long cid);
	
	List<Category> queryNameByIds(List<Long> ids);

	List<Category> queryCategoriesByCids(List<Long> ids);

}
