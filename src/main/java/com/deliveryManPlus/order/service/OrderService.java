package com.deliveryManPlus.order.service;

import com.deliveryManPlus.order.dto.OrderResponseDto;
import com.deliveryManPlus.order.dto.OrderStatusRejectDto;
import com.deliveryManPlus.order.dto.OrderStatusUpdateDto;
import com.deliveryManPlus.auth.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    void createOrder();

    List<OrderResponseDto> findOrderForUser(User user);

    List<OrderResponseDto> findOrderForOwner(Long shopId);

    OrderResponseDto updateStatus(Long shopId, Long orderId, @Valid OrderStatusUpdateDto dto);

    void reject(Long shopId, Long orderId, @Valid OrderStatusRejectDto dto);
}
