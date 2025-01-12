package com.deliveryManPlus.dto.shop;

import com.deliveryManPlus.constant.ShopStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShopStatusRequestDto {
    @NotNull
    private ShopStatus status;
}
