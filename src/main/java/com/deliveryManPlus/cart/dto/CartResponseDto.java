package com.deliveryManPlus.cart.dto;

import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.shop.entity.Shop;
import lombok.Getter;

import java.util.List;

@Getter
public class CartResponseDto {
    private final Long cartId;
    private final Long shopId;
    private final String shopName;

    private final List<CartMenuResponseDto> cartMenuResponseDtoList;

    public CartResponseDto(Cart cart) {
        this.cartId = cart.getId();
        Shop shop = cart.getCartMenuList().get(0).getMenu().getShop();
        this.shopId = shop.getId();
        this.shopName = shop.getName();
        this.cartMenuResponseDtoList = cart.getCartMenuList().stream()
                .map(CartMenuResponseDto::new)
                .toList();
    }
}
