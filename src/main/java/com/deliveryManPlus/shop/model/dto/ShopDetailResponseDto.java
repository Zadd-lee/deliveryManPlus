package com.deliveryManPlus.shop.model.dto;

import com.deliveryManPlus.shop.model.entity.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ShopDetailResponseDto {
    private Long id;
    private String name;


    public ShopDetailResponseDto(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
    }
}
