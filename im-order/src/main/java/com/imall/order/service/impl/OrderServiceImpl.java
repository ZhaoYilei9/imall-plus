package com.imall.order.service.impl;

import com.imall.auth.entity.UserInfo;
import com.imall.common.utils.IdWorker;
import com.imall.enums.ExceptionEnum;
import com.imall.exception.ImException;
import com.imall.order.client.GoodsClient;
import com.imall.order.dto.OrderDto;
import com.imall.order.enums.OrderStatusEnum;
import com.imall.order.filter.UserInterceptor;
import com.imall.order.mapper.OrderDetailMapper;
import com.imall.order.mapper.OrderMapper;
import com.imall.order.mapper.OrderStatusMapper;
import com.imall.order.pojo.Order;
import com.imall.order.pojo.OrderDetail;
import com.imall.order.pojo.OrderStatus;
import com.imall.order.service.OrderService;
import com.imall.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.awt.image.ImagingOpException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private GoodsClient goodClient;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;



    @Override
    public Long createOrder(OrderDto orderDto) {
        //1.采用idworker生成订单id
        long orderId = idWorker.nextId();
        //2.填充order
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCreateTime(new Date());
        order.setInvoiceType(orderDto.getPaymentType());
        order.setPostFee(0L);
        //3.获取用户信息
        UserInfo loginUser = UserInterceptor.getLoginUser();
        order.setUserId(loginUser.getId().toString());
        order.setBuyerNick(loginUser.getUsername());
        order.setBuyerRate(false);
        //4.查询收货人信息//todo
        //5.计算totalPay,填充订单详情
        //5.1首先把orderDto转化为map,key为skuId,value为num
        Map<Long, Integer> skuNumMap = orderDto.getCarts().stream().collect(Collectors.toMap(c -> c.getSkuId(), c -> c.getNum()));
        List<Long> ids = new ArrayList<>(skuNumMap.keySet());
        List<Sku> skus = goodClient.querySkuByIds(ids);
        if (CollectionUtils.isEmpty(skus)) {
            throw new ImException(ExceptionEnum.SKU_NOT_FOUND);
        }
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        Double totalPay = totalPay(skuNumMap, skus,orderDetails,order);
        order.setActualPay(totalPay.longValue() + order.getPostFee() - 0L);//todo 减去优惠券
        order.setTotalPay(totalPay.longValue());
        orderMapper.insertSelective(order);
        int counts = orderDetailMapper.insertList(orderDetails);
        if (counts < 1){
            throw new ImException(ExceptionEnum.ORDER_DETAIL_INSERT_ERROR);
        }

        //6.填充orderStatus
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setCreateTime(new Date());
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus(OrderStatusEnum.INIT.value());
        orderStatusMapper.insertSelective(orderStatus);
        //7.减库存
        goodClient.decreaseStock(orderDto.getCarts());
        return null;
    }

    private Double totalPay(Map<Long, Integer> skuNumMap, List<Sku> skus, ArrayList<OrderDetail> orderDetails, Order order) {
        Double totalPay = 0.0;
        for (Sku sku : skus) {
            totalPay += sku.getPrice() * skuNumMap.get(sku.getId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(idWorker.nextId());
            orderDetail.setImage(StringUtils.substringBefore(sku.getImages(),","));
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setNum(skuNumMap.get(sku.getId()));
            orderDetail.setOwnSpec(sku.getOwnSpec());
            orderDetail.setPrice(sku.getPrice());
            orderDetail.setTitle(sku.getTitle());
            orderDetails.add(orderDetail);
        }
        return totalPay;
    }
}
