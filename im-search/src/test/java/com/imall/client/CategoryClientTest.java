//package com.imall.client;
//
//import static org.junit.Assert.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.imall.ImSearchService;
//import com.imall.pojo.Category;
//import com.imall.response.ImallResult;
//
//import lombok.extern.slf4j.Slf4j;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ImSearchService.class)
//@Slf4j
//public class CategoryClientTest {
//
//	@Autowired
//    private CategoryClient categoryClient;
//
//    @Test
//    public void testQueryCategories() {
//        List<String> names = this.categoryClient.queryNameByIds(Arrays.asList(1L, 2L));
//        log.info("*****************************:{}",names);
//
//    }
//
//}
