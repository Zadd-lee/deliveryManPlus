package com.deliveryManPlus.cart.service;

import com.deliveryManPlus.cart.dto.CartCreateMenuDto;

public interface CartService {
    void addCartMenu(Long shopId, Long menuId, CartCreateMenuDto dto);
}
