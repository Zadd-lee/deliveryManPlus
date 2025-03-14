package com.deliveryManPlus.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CartMenuOptionDetailRequestDto {
    List<Long> cartMenuOptionDetailIdList;
}
