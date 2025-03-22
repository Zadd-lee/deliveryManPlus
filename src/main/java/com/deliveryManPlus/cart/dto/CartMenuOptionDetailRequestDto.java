package com.deliveryManPlus.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "장바구니 메뉴 옵션 세부 요청 DTO")
@NoArgsConstructor
@Getter
public class CartMenuOptionDetailRequestDto {
    @Schema(description = "메뉴 옵션 세부 ID 리스트", example = "[1, 2, 3]")
    List<Long> cartMenuOptionDetailIdList;
}
