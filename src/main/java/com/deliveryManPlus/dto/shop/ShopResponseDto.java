package com.deliveryManPlus.dto.shop;

import com.deliveryManPlus.constant.ShopStatus;
import com.deliveryManPlus.entity.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ShopResponseDto {
    private Long id;
    private String name;
    private String address;
    private ShopStatus status;


    public ShopResponseDto(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.address = shop.getAddress();
        this.status = shop.getStatus();
    }
}
