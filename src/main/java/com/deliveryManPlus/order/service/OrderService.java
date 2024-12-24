package com.deliveryManPlus.order.service;

import com.deliveryManPlus.order.model.dto.CreateOrderRequestDto;
import jakarta.validation.Valid;

public interface OrderService {
    void createOrder(Long id, @Valid CreateOrderRequestDto menuList);
}
