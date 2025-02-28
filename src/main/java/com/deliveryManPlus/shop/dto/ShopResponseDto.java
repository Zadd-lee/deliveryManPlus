package com.deliveryManPlus.shop.dto;

import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.entity.Shop;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "상점 응답 정보")
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
