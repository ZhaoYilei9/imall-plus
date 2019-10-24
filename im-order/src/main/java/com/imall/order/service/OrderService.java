package com.imall.order.service;

import com.imall.order.dto.OrderDto;

public interface OrderService {
    Long createOrder(OrderDto orderDto);
}
