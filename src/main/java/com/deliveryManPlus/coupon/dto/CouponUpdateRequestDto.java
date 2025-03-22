package com.deliveryManPlus.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "쿠폰 수정 요청")
@NoArgsConstructor
@Getter
public class CouponUpdateRequestDto {
    @Schema(description = "쿠폰 이름", example = "10% 할인")
    @NotBlank
    private String name;

    @Schema(description = "할인 금액", example = "1000.00")
    @NotNull
    private BigDecimal discountPrice;

    @Schema(description = "수량", example = "100")
    @Positive
    private Integer quantity;
}