package com.deliveryManPlus.dto.order;

import com.deliveryManPlus.constant.error.OrderStatus;
import com.deliveryManPlus.entity.Order;

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
