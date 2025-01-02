package com.deliveryManPlus.shop.model.dto;

import com.deliveryManPlus.shop.constant.ShopStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShopStatusRequestDto {
    @NotNull
    private ShopStatus status;
}
