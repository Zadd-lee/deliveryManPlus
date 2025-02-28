package com.deliveryManPlus.dto.shop;

import com.deliveryManPlus.dto.menu.MenuSimpleResponseDto;
import com.deliveryManPlus.entity.Menu;
import com.deliveryManPlus.constant.ShopStatus;
import com.deliveryManPlus.entity.Shop;
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
