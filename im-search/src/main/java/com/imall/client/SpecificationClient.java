package com.imall.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.imall.api.SpecificationApi;

@FeignClient(value = "item-service")
public interface SpecificationClient extends SpecificationApi{

}
