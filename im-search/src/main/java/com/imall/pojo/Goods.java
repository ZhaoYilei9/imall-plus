package com.imall.pojo;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@Document(indexName = "goods", type = "docs", shards = 1, replicas = 0)
@Getter
@Setter
public class Goods {

    @Id
    private Long id;//spuId
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;
    private Long brandId;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private Date createTime;
    private List<Long> price;
    //sku信息的json结构
    @Field(type = FieldType.Keyword, index = false)
    private String skus;
    //可搜索的商品规格参数当存储到索引库时，
    // elasticsearch会处理为两个字段：
    //specs.内存 ： [4G,6G]
    //specs.颜色：红色
    private Map<String,Object> specs;

}