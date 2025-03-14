package com.deliveryManPlus.order.service;

import com.deliveryManPlus.common.exception.constant.errorcode.OrderStatus;
import com.deliveryManPlus.order.dto.OrderDetailResponseDto;
import com.deliveryManPlus.order.dto.OrderSimpleResponseDto;
import org.springframework.data.domain.Page;

public interface OrderService {
    void createOrder();

    Page<OrderDetailResponseDto> findOrderForOwner(Long shopId, int page, int size);

    void updateStatus(Long shopId, Long orderId, OrderStatus orderStatus);

    void reject(Long shopId, Long orderId, String rejectReason);

    Page<OrderSimpleResponseDto> findAllOrderForUser(int page, int size);

    OrderDetailResponseDto findOrderForUser(Long orderId);
}
