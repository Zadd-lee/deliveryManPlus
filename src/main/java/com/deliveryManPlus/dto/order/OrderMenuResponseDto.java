package com.deliveryManPlus.dto.order;

import com.deliveryManPlus.entity.OrderMenu;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderMenuResponseDto {
    private final String name;
    private final BigDecimal price;
    private final int quantity;

    public OrderMenuResponseDto(OrderMenu orderMenu) {
        this.name = orderMenu.getMenuHistory().getName();
        this.price = orderMenu.getMenuHistory().getPrice();
        this.quantity = orderMenu.getQuantity();
    }
}
