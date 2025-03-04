package com.deliveryManPlus.cart.dto;

import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.cart.entity.CartMenu;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.menu.entity.MenuOptionDetail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CartCreateMenuDto {

    //옵션
    @Valid
    List<CartMenuOptionRequestDto> cartMenuOptionDtoList;

    //수량
    @Positive
    @NotNull
    private int quantity;

    public CartMenu toEntity(Menu menu, Cart cart, List<MenuOptionDetail> menuOptionDetailList) {
        return CartMenu.builder()
                .menu(menu)
                .cart(cart)
                .menuOptionDetailList(menuOptionDetailList)
                .quantity(this.quantity)
                .build();
    }

    public List<Long> getAllCartMenuOptionDtoList() {
        return this.cartMenuOptionDtoList.stream()
                .flatMap(x -> x.getMenuOptionDetailIdList()
                        .stream())
                .toList();

    }

}
