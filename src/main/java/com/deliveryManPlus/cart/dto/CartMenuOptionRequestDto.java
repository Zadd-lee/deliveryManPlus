package com.deliveryManPlus.cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CartMenuOptionRequestDto {
    @NotNull
    private Long menuOptionId;
    @NotNull
    private List<Long> menuOptionDetailIdList;

}
