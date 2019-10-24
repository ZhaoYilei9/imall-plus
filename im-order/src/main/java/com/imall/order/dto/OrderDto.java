package com.imall.order.dto;

import com.imall.common.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @NotNull
    private Long addressId ;

    @NotNull
    private Integer paymentType ;

    @NotNull
    private List<CartDto> carts;
}
