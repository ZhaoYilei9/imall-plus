package com.imall.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imall.pojo.Category;
import com.imall.response.ImallResult;

@RequestMapping("category")
public interface CategoryApi {
	
//	List<Category> queryCategoriesByPid(Long pid);

//	Category queryCategoryByCid(Long cid);
	@RequestMapping("names")
	List<String> queryNameByIds(@RequestParam("ids") List<Long> ids);
}
