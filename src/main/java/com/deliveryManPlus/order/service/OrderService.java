package com.deliveryManPlus.order.service;

import com.deliveryManPlus.order.model.dto.CreateOrderRequestDto;
import com.deliveryManPlus.order.model.dto.OrderResponseDto;
import com.deliveryManPlus.order.model.dto.OrderStatusRejectDto;
import com.deliveryManPlus.order.model.dto.OrderStatusUpdateDto;
import com.deliveryManPlus.user.model.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    void createOrder(User user, @Valid CreateOrderRequestDto menuList);

    List<OrderResponseDto> findOrderForUser(User user);

    List<OrderResponseDto> findOrderForOwner(Long shopId);

    OrderResponseDto updateStatus(Long shopId, Long orderId, @Valid OrderStatusUpdateDto dto);

    void reject(Long shopId, Long orderId, @Valid OrderStatusRejectDto dto);
}
