package com.deliveryManPlus.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "장바구니 메뉴 옵션 요청 DTO")
@NoArgsConstructor
@Getter
public class CartMenuOptionRequestDto {
    @Schema(description = "메뉴 옵션 ID", example = "1")
    @NotNull
    private Long menuOptionId;
    @Schema(description = "메뉴 세부 옵션 ID 리스트", example = "[1, 2, 3]")
    @NotNull
    private List<Long> menuOptionDetailIdList;

}
