package com.imall.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.imall.pojo.Goods;

public interface GoodsRepository extends ElasticsearchRepository<Goods, Long>{

}
