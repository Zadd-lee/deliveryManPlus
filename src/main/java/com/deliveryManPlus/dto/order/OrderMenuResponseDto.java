package com.deliveryManPlus.dto.order;

import com.deliveryManPlus.entity.OrderMenu;

import java.math.BigDecimal;

public class OrderMenuResponseDto {
    private String name;
    private BigDecimal price;
    private int quantity;

    public OrderMenuResponseDto(OrderMenu orderMenu) {
        this.name = orderMenu.getMenuHistory().getName();
        this.price = orderMenu.getMenuHistory().getPrice();
        this.quantity = orderMenu.getQuantity();
    }
}
