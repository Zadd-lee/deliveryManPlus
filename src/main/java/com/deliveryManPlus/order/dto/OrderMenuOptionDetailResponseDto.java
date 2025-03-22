package com.deliveryManPlus.order.dto;

import com.deliveryManPlus.order.entity.OrderMenuOptionDetail;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderMenuOptionDetailResponseDto {
    private final String name;
    private final BigDecimal price;

    public OrderMenuOptionDetailResponseDto(OrderMenuOptionDetail orderMenuOptionDetail) {
        this.name = orderMenuOptionDetail.getName();
        this.price = orderMenuOptionDetail.getPrice();

    }
}
