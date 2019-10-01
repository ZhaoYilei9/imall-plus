package com.imall.page.client;

import com.imall.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "item-service")
public interface BrandClient extends BrandApi{

}
