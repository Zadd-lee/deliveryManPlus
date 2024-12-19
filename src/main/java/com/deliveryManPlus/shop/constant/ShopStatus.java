package com.deliveryManPlus.shop.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ShopStatus {
    OPEN,CLOSED,CLOSED_DOWN;
    @JsonCreator
    public static ShopStatus fromValue(String value) {
        return ShopStatus.valueOf(value.toUpperCase());
    }
}
