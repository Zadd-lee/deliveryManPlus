package com.deliveryManPlus.cart.dto;

import com.deliveryManPlus.cart.entity.CartMenu;
import com.deliveryManPlus.cart.entity.CartMenuOptionDetail;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class CartMenuResponseDto {

    private final Long menuId;
    private final String menuName;
    private final BigDecimal menuPrice;
    private final int quantity;
    private final List<CartMenuOptionResponseDto> cartMenuOptionDtoList;

    public CartMenuResponseDto(CartMenu cartMenu) {
        this.menuId = cartMenu.getMenu().getId();
        this.menuName = cartMenu.getMenu().getName();
        this.menuPrice = cartMenu.getMenu().getPrice();
        this.quantity = cartMenu.getQuantity();
        this.cartMenuOptionDtoList = cartMenu.getCartMenuOptionDetailList()
                .stream()
                .map(CartMenuOptionDetail::getMenuOptionDetail)
                .map(CartMenuOptionResponseDto::new)
                .toList();

    }
}
