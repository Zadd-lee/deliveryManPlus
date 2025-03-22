package com.deliveryManPlus.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "주문 생성 요청")
@NoArgsConstructor
@Getter
public class OrderCreateRequestDto {
    @Schema(description = "쿠폰 식별자", example = "1")
    private Long couponId;


}
