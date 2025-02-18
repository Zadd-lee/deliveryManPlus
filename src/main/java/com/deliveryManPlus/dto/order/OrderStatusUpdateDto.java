package com.deliveryManPlus.dto.order;

import com.deliveryManPlus.constant.error.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderStatusUpdateDto {
    @Schema(description = "주문 상태", allowableValues = {"SUBMIT","SUBMIT", "COOKING",
            "DELIVERY_START", "COMPLETED", "REJECTED"}
            ,example = "SUBMIT")
    @NotNull
    private OrderStatus status;
}
