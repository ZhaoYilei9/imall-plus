package com.imall.repo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.imall.ImSearchService;
import com.imall.pojo.Goods;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImSearchService.class)
public class GoodsRepositoryTest {

	@Autowired
	private GoodsRepository goodsRepository;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Test
	public void createIndex() {
		// 创建索引
		this.elasticsearchTemplate.createIndex(Goods.class);
		// 配置映射
		this.elasticsearchTemplate.putMapping(Goods.class);
	}
}