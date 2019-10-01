package com.imall.page.client;


import com.imall.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi{

	

}
