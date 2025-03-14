package com.deliveryManPlus.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "주문 생성 요청")
@NoArgsConstructor
@Getter
public class OrderCreateRequestDto {
    @Schema(description = "식당 식별자", example = "1")
    @NotNull
    private Long shopId;
    @Schema(description = "메뉴 식별자", example = "1")
    @NotNull
    private Long menuId;


}
