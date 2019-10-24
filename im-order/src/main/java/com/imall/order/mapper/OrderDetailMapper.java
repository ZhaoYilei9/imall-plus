package com.imall.order.mapper;

import com.imall.order.pojo.OrderDetail;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;


public interface OrderDetailMapper extends Mapper<OrderDetail> , InsertListMapper<OrderDetail> {
}