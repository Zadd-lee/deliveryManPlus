package com.deliveryManPlus.shop.dto;

import com.deliveryManPlus.menu.dto.menu.MenuSimpleResponseDto;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.entity.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
public class ShopDetailResponseDto {
    private Long id;
    private String name;
    private String address;
    private BigDecimal minimumOrderAmount;
    private ShopStatus status;
    private String openAt;
    private String closedAt;
    private String closedDay;

    private List<MenuSimpleResponseDto> menuList;


    public ShopDetailResponseDto(Shop shop, List<Menu> menuList) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.address = shop.getAddress();
        this.minimumOrderAmount = shop.getMinimumOrderAmount();
        this.status = shop.getStatus();
        this.openAt = shop.getOpenAt();
        this.closedAt= shop.getClosedAt();
        this.closedDay = shop.getClosedDay();
        this.menuList = menuList.stream()
                .map(MenuSimpleResponseDto::new)
                .toList();
    }
}
