package com.deliveryManPlus.dto.order;

import com.deliveryManPlus.constant.error.OrderStatus;
import com.deliveryManPlus.entity.Order;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;

import java.util.List;

@ApiResponse(description = "주문 응답")
@Getter
public class OrderResponseDto {
    private final Long orderId;
    private final OrderStatus orderStatus;
    private final List<OrderMenuResponseDto> orderMenuResponseDtoList;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus();
        this.orderMenuResponseDtoList = order.getOrderMenu().stream()
                .map(OrderMenuResponseDto::new)
                .toList();
    }
}
