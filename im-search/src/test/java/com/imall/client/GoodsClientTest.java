package com.imall.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imall.ImSearchService;
import com.imall.pojo.Sku;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImSearchService.class)
@Slf4j
public class GoodsClientTest {

	@Autowired
	private GoodsClient goodsClient;
	@Test
    public void testQuerySkuList() {
		List<Sku> skuList = goodsClient.querySkuList(226L);
		log.info("****** skuList:{}", skuList);
	}
}
