package com.deliveryManPlus.order.model.dto;

import com.deliveryManPlus.order.constant.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderStatusUpdateDto {
    @NotNull
    private OrderStatus status;
}
