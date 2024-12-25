package com.deliveryManPlus.order.service;

import com.deliveryManPlus.order.model.dto.CreateOrderRequestDto;
import com.deliveryManPlus.order.model.dto.OrderResponseDto;
import com.deliveryManPlus.order.model.dto.OrderStatusUpdateDto;
import jakarta.validation.Valid;

public interface OrderService {
    void createOrder(Long id, @Valid CreateOrderRequestDto menuList);

    OrderResponseDto updateStatus(Long id, Long shopId, Long orderId, @Valid OrderStatusUpdateDto dto);
}
