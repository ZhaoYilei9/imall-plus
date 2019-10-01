package com.imall.page.client;

import com.imall.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(value = "item-service")
@Component
public interface SpecificationClient extends SpecificationApi{

}
