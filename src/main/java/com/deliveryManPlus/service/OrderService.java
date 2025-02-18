package com.deliveryManPlus.service;

import com.deliveryManPlus.dto.order.OrderCreateRequestDto;
import com.deliveryManPlus.dto.order.OrderResponseDto;
import com.deliveryManPlus.dto.order.OrderStatusRejectDto;
import com.deliveryManPlus.dto.order.OrderStatusUpdateDto;
import com.deliveryManPlus.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    void createOrder(User user, @Valid OrderCreateRequestDto menuList);

    List<OrderResponseDto> findOrderForUser(User user);

    List<OrderResponseDto> findOrderForOwner(Long shopId);

    OrderResponseDto updateStatus(Long shopId, Long orderId, @Valid OrderStatusUpdateDto dto);

    void reject(Long shopId, Long orderId, @Valid OrderStatusRejectDto dto);
}
