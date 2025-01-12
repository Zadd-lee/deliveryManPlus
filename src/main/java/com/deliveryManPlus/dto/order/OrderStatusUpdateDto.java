package com.deliveryManPlus.dto.order;

import com.deliveryManPlus.constant.error.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderStatusUpdateDto {
    @NotNull
    private OrderStatus status;
}
