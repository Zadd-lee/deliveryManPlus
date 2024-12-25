package com.deliveryManPlus.order.service;

import com.deliveryManPlus.order.model.dto.CreateOrderRequestDto;
import com.deliveryManPlus.order.model.dto.OrderResponseDto;
import com.deliveryManPlus.order.model.dto.OrderStatusRejectDto;
import com.deliveryManPlus.order.model.dto.OrderStatusUpdateDto;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    void createOrder(Long id, @Valid CreateOrderRequestDto menuList);

    List<OrderResponseDto> findOrderForUser(Long userId);

    List<OrderResponseDto> findOrderForOwner(Long userId, Long shopId);

    OrderResponseDto updateStatus(Long id, Long shopId, Long orderId, @Valid OrderStatusUpdateDto dto);

    void reject(Long userid, Long shopId, Long orderId, @Valid OrderStatusRejectDto dto);
}
