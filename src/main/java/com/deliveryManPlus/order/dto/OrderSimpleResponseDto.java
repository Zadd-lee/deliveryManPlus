package com.deliveryManPlus.order.dto;

import com.deliveryManPlus.common.exception.constant.errorcode.OrderStatus;
import com.deliveryManPlus.order.entity.Order;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiResponse(description = "주문 응답")
@Getter
public class OrderSimpleResponseDto {
    private final Long orderId;
    private final OrderStatus orderStatus;
    private final LocalDateTime orderDate;


    //가게 정보
    private final Long shopId;
    private final String shopName;

    //주문 메뉴 정보
    private final String menuName;
    private final int menuQuantity;
    private final BigDecimal totalPrice;


    public OrderSimpleResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus();
        this.orderDate = order.getCreatedAt();
        this.shopId = order.getShop().getId();
        this.shopName = order.getShop().getName();
        this.menuName = order.getOrderMenu().get(0).getName();
        this.menuQuantity = order.getOrderMenu().size();
        this.totalPrice = order.getTotalPrice();
    }
}
