package com.imall.service;

import java.util.List;

import com.imall.pojo.Category;

public interface CategoryService {

	List<Category> queryCategoriesByPid(Long pid);


}
