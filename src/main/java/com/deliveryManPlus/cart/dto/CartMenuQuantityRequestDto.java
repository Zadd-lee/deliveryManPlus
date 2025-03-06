package com.deliveryManPlus.cart.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartMenuQuantityRequestDto {
    @Positive
    int quantity;
}
