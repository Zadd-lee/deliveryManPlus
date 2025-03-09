package com.deliveryManPlus.order.service;

import com.deliveryManPlus.order.dto.OrderDetailResponseDto;
import com.deliveryManPlus.order.dto.OrderSimpleResponseDto;
import com.deliveryManPlus.order.dto.OrderStatusRejectDto;
import com.deliveryManPlus.order.dto.OrderStatusUpdateDto;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    void createOrder();

    List<OrderDetailResponseDto> findOrderForOwner(Long shopId);

    OrderSimpleResponseDto updateStatus(Long shopId, Long orderId, @Valid OrderStatusUpdateDto dto);

    void reject(Long shopId, Long orderId, @Valid OrderStatusRejectDto dto);

    List<OrderSimpleResponseDto> findAllOrderForUser();

    OrderDetailResponseDto findOrderForUser(Long orderId);
}
