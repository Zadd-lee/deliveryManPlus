package com.deliveryManPlus.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderCreateRequestDto {
    @NotNull
    private Long shopId;
    @NotNull
    private Long menuId;


}
