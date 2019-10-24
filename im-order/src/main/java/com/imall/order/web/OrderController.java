package com.imall.order.web;


import com.imall.common.utils.IdWorker;
import com.imall.order.dto.OrderDto;
import com.imall.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@Api("订单服务接口")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ApiOperation(value = "创建订单接口，返回订单编号", notes = "创建订单")
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto){
        orderService.createOrder(orderDto);
        return ResponseEntity.ok(null);
    }
}
