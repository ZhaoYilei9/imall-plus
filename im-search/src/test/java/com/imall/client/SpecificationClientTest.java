package com.imall.client;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imall.ImSearchService;
import com.imall.response.ImallResult;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImSearchService.class)
@Slf4j
public class SpecificationClientTest {

	@Autowired
    private SpecificationClient specificationClient;

    @Test
    public void testQueryCategories() {
    	ImallResult imallResult = specificationClient.getSpecParamsByCid(76L);
    	log.info("***imallResult-client-test:{}***", imallResult);
    }
}
