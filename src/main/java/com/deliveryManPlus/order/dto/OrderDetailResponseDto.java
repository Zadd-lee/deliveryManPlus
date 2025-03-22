package com.deliveryManPlus.order.dto;

import com.deliveryManPlus.common.exception.constant.errorcode.OrderStatus;
import com.deliveryManPlus.order.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "주문 상세 응답")
@Getter
public class OrderDetailResponseDto {
    @Schema(description = "주문 ID", example = "1")
    private final Long orderId;

    @Schema(description = "주문 상태", example = "COMPLETED")
    private final OrderStatus orderStatus;

    @Schema(description = "주문 날짜", example = "2023-01-01T12:00:00")
    private final LocalDateTime orderDate;

    @Schema(description = "가게 ID", example = "1")
    private final Long shopId;

    @Schema(description = "가게 이름", example = "Best Shop")
    private final String shopName;

    @Schema(description = "주문 메뉴 목록")
    private final List<OrderMenuResponseDto> orderMenuList;

    @Schema(description = "총 가격", example = "20.00")
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