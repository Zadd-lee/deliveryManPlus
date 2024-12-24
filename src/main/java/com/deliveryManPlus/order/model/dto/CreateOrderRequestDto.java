package com.deliveryManPlus.order.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateOrderRequestDto {
    @NotNull
    private Long shopId;
    @NotNull
    private Long menuId;


}
