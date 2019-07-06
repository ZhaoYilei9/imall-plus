package com.imall.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imall.ImSearchService;
import com.imall.client.GoodsClient;
import com.imall.common.pojo.PageResult;
import com.imall.exception.ImException;
import com.imall.pojo.Goods;
import com.imall.pojo.Spu;
import com.imall.repo.GoodsRepository;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImSearchService.class)
@Slf4j
public class IndexServiceTest {

	@Autowired
	private GoodsClient goodsClient;

	@Autowired
	private IndexService indexService;

	@Autowired
	private GoodsRepository goodsRepository;

	@Test
	public void loadData() {
		int page = 1;
		int rows = 100;
		int size = 0;
//		log.info("*********indexService:{}", indexService);
		do {
			// 查询spu
			PageResult<Spu> result = this.goodsClient.spuList(null, true, page, rows);
			ArrayList<Goods> goodList = new ArrayList<>();
			List<Spu> spus = result.getItems();
			size = spus.size();
			log.info("******spus:{}", spus);
			for (Spu spu : spus) {
				try {
					Goods g = indexService.buildGoods(spu);
					log.info("******goods:{}", g);
					goodList.add(g);
				} catch (Exception e) {
					log.error("****error***:{}",e.getMessage());
				}
			}
			log.info("******goodsList:{}", goodList);
			if (goodList.size() > 0) {
				this.goodsRepository.saveAll(goodList);

			}

			page++;
		} while (size == 100);
	}
	
}
