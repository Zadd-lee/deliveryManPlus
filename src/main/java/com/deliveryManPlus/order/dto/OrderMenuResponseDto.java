package com.deliveryManPlus.order.dto;

import com.deliveryManPlus.order.entity.OrderMenu;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class OrderMenuResponseDto {

    private final BigDecimal price;
    private final int quantity;
    private final List<OrderMenuOptionDetailResponseDto> orderMenuOptionDetailList;

    public OrderMenuResponseDto(OrderMenu orderMenu) {
        this.price = orderMenu.getPrice();
        this.quantity = orderMenu.getQuantity();
        this.orderMenuOptionDetailList = orderMenu.getOrderMenuOptionDetail()
                .stream()
                .map(OrderMenuOptionDetailResponseDto::new)
                .toList();
    }
}
