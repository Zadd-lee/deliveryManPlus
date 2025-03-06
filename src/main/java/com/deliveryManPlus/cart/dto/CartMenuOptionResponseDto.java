package com.deliveryManPlus.cart.dto;

import com.deliveryManPlus.cart.entity.CartMenuOptionDetail;
import com.deliveryManPlus.menu.entity.MenuOptionDetail;
import lombok.Getter;

import java.util.List;

@Getter
public class CartMenuOptionResponseDto {
    private final Long menuOptionId;
    private final String menuOptionName;
    private final List<String> menuOptionDetailNameList;

    public CartMenuOptionResponseDto(MenuOptionDetail menuOptionDetail, List<CartMenuOptionDetail> cartMenuOptionDetailList) {
        this.menuOptionId = menuOptionDetail.getMenuOption().getId();
        this.menuOptionName = menuOptionDetail.getMenuOption().getTitle();
        this.menuOptionDetailNameList = cartMenuOptionDetailList.stream()
                .map(x -> x.getMenuOptionDetail().getName())
                .toList();
    }
}
