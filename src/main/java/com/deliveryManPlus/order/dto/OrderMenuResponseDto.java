package com.deliveryManPlus.order.dto;

import com.deliveryManPlus.order.entity.OrderMenu;
import lombok.Getter;

@Getter
public class OrderMenuResponseDto {

    private final int quantity;

    public OrderMenuResponseDto(OrderMenu orderMenu) {
        this.quantity = orderMenu.getQuantity();
    }
}
