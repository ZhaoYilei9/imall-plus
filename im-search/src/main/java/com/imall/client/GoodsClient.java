package com.imall.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.imall.api.GoodsApi;

@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {

}
