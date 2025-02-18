package com.deliveryManPlus.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderStatusRejectDto {
    @Schema(description = "주문 거절 사유", example = "재료 부족")
    private String rejectReason;
}
