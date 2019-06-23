package com.imall.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imall.api.CategoryApi;
import com.imall.pojo.Category;

@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi{

}
