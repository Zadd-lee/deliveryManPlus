package com.deliveryManPlus.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Schema(description = "장바구니 메뉴 수량 요청 DTO")
@NoArgsConstructor
@Getter
public class CartMenuQuantityRequestDto {
    @Schema(description = "수량", example = "1")
    @Positive
    int quantity;
}
