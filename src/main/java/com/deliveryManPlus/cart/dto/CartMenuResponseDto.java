package com.deliveryManPlus.cart.dto;

import com.deliveryManPlus.cart.entity.CartMenu;
import com.deliveryManPlus.cart.entity.CartMenuOptionDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class CartMenuResponseDto {

    @Schema(description = "메뉴 ID", example = "1")
    private final Long menuId;

    @Schema(description = "메뉴 이름", example = "금니 정식")
    private final String menuName;

    @Schema(description = "메뉴 가격", example = "15000")
    private final BigDecimal menuPrice;

    @Schema(description = "수량", example = "2")
    private final int quantity;

    @Schema(description = "메뉴 옵션 리스트")
    private final List<CartMenuOptionResponseDto> cartMenuOptionDtoList;

    public CartMenuResponseDto(CartMenu cartMenu) {
        this.menuId = cartMenu.getMenu().getId();
        this.menuName = cartMenu.getMenu().getName();
        this.menuPrice = cartMenu.getMenu().getPrice();
        this.quantity = cartMenu.getQuantity();
        this.cartMenuOptionDtoList = cartMenu.getCartMenuOptionDetailList()
                .stream()
                .map(CartMenuOptionDetail::getMenuOptionDetail)
                .map(menuOptionDetail -> new CartMenuOptionResponseDto(menuOptionDetail, cartMenu.getCartMenuOptionDetailList()))
                .toList();
    }
}