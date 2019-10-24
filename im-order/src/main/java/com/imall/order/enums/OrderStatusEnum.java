package com.imall.order.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum OrderStatusEnum {
    INIT(1,"未付款"),
    PAY_UP(2,"已付款，未发货"),
    DELIVERED(3,"已发货，未确认"),
    CONFIRMED(4,"已确认,未评价"),
    CLOSED(5,"交易关闭"),
    COMMENTED(6,"已评价")


    ;
    int code;
    String msg;
    public Integer value() {
        return this.code;
    }

}
