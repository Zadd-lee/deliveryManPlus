package com.deliveryManPlus.common.utils;

import com.deliveryManPlus.cart.entity.CartMenu;

import java.math.BigDecimal;

public class Calculator {
    public static BigDecimal calculateMenuPrice(CartMenu cartMenu) {
        BigDecimal price = cartMenu.getMenu().getPrice();
        BigDecimal optionPrice = cartMenu.getCartMenuOptionDetailList()
                .stream()
                .map(x -> x.getMenuOptionDetail().getOptionPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return price.add(optionPrice);
    }


}
