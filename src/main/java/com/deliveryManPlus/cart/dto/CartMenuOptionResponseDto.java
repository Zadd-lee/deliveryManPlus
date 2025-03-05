package com.deliveryManPlus.cart.dto;

import com.deliveryManPlus.menu.entity.MenuOptionDetail;
import lombok.Getter;

import java.util.List;

@Getter
public class CartMenuOptionResponseDto {
    private final Long menuOptionId;
    private final String menuOptionName;
    private final List<String> menuOptionDetailNameList;

    public CartMenuOptionResponseDto(MenuOptionDetail menuOptionDetail) {
        this.menuOptionId = menuOptionDetail.getMenuOption().getId();
        this.menuOptionName = menuOptionDetail.getMenuOption().getTitle();
        this.menuOptionDetailNameList = menuOptionDetail.getMenuOption().getMenuOptionDetailList()
                .stream()
                .map(MenuOptionDetail::getName)
                .toList();
    }
}
