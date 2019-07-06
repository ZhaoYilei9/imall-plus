package com.imall.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import com.imall.api.SpecificationApi;

@FeignClient(value = "item-service")
@Component
public interface SpecificationClient extends SpecificationApi{

}
