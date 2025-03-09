package com.deliveryManPlus.order.dto;

import com.deliveryManPlus.common.exception.constant.errorcode.OrderStatus;
import com.deliveryManPlus.order.entity.Order;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApiResponse(description = "주문 응답")
@Getter
public class OrderDetailResponseDto {
    private final Long orderId;
    private final OrderStatus orderStatus;
    private final LocalDateTime orderDate;


    //가게 정보
    private final Long shopId;
    private final String shopName;

    //주문 메뉴 정보
    private final List<OrderMenuResponseDto> orderMenuList;
    private final BigDecimal totalPrice;


    public OrderDetailResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus();
        this.orderDate = order.getCreatedAt();
        this.shopId = order.getShop().getId();
        this.shopName = order.getShop().getName();
        this.orderMenuList = order.getOrderMenu()
                .stream()
                .map(OrderMenuResponseDto::new)
                .toList();
        this.totalPrice = order.getTotalPrice();
    }
}
