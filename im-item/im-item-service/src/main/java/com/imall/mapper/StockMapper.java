package com.imall.mapper;

import com.imall.pojo.Stock;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface StockMapper extends Mapper<Stock>, SelectByIdListMapper<Stock, Long> {

    @Update("UPDATE tb_stock SET stock = stock -#{num} where sku_id = #{skuId} and stock > #{num} ;")
    void decreaseStock(Long skuId, Integer num);
}