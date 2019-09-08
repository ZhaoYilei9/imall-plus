package com.imall.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import com.imall.api.BrandApi;

@Component
@FeignClient(value = "item-service")
public interface BrandClient extends BrandApi{

}
