package com.deliveryManPlus.order.model.dto;

import com.deliveryManPlus.order.constant.OrderStatus;
import com.deliveryManPlus.order.model.entity.Order;

import java.util.List;

public class OrderResponseDto {
    private Long orderId;
    private OrderStatus orderStatus;
    private List<OrderMenuResponseDto> orderMenuResponseDtoList;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus();
        this.orderMenuResponseDtoList = order.getOrderMenu().stream()
                .map(OrderMenuResponseDto::new)
                .toList();
    }
}
