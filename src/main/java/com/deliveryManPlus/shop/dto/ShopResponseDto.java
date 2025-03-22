package com.deliveryManPlus.shop.dto;

import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.entity.Shop;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Schema(description = "상점 응답 정보")
@NoArgsConstructor
@Getter
public class ShopResponseDto {
    private Long id;
    private String name;
    private String address;
    private ShopStatus status;

    private String path;


    public ShopResponseDto(Shop shop, List<Image> imageList) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.address = shop.getAddress();
        this.status = shop.getStatus();
        this.path = getPath(shop, imageList);
    }

    private static String getPath(Shop shop, List<Image> imageList) {

        return imageList.stream()
                .filter(image -> Objects.equals(image.getImageTarget().getTargetId(), shop.getId()))
                .map(Image::getPath)
                .findFirst()
                .orElseGet(() -> "");
    }
}
