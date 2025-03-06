package com.deliveryManPlus.cart.service;

import com.deliveryManPlus.cart.dto.CartMenuOptionDetailRequestDto;
import com.deliveryManPlus.cart.dto.CartMenuRequestDto;
import com.deliveryManPlus.cart.dto.CartResponseDto;

public interface CartService {
    void addCartMenu(Long shopId, Long menuId, CartMenuRequestDto dto);

    CartResponseDto findCartList();

    void updateCartMenuOptionDetail(Long menuId, CartMenuOptionDetailRequestDto dto);

    void updateCartMenuQuantity(Long menuId, int quantity);

    void deleteCartMenu(Long menuId);

}
