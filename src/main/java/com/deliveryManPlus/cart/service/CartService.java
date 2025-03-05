package com.deliveryManPlus.cart.service;

import com.deliveryManPlus.cart.dto.CartCreateMenuDto;
import com.deliveryManPlus.cart.dto.CartResponseDto;

public interface CartService {
    void addCartMenu(Long shopId, Long menuId, CartCreateMenuDto dto);

    CartResponseDto findCartList();
}
