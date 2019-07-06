package com.imall.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import com.imall.api.GoodsApi;

@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {

}
