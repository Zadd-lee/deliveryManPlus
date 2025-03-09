package com.deliveryManPlus.order.service;

import com.deliveryManPlus.common.exception.constant.errorcode.OrderStatus;
import com.deliveryManPlus.order.dto.OrderDetailResponseDto;
import com.deliveryManPlus.order.dto.OrderSimpleResponseDto;
import com.deliveryManPlus.order.dto.OrderStatusRejectDto;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    void createOrder();

    List<OrderDetailResponseDto> findOrderForOwner(Long shopId);

    void updateStatus(Long shopId, Long orderId, OrderStatus orderStatus);

    void reject(Long shopId, Long orderId, @Valid OrderStatusRejectDto dto);

    List<OrderSimpleResponseDto> findAllOrderForUser();

    OrderDetailResponseDto findOrderForUser(Long orderId);
}
