package com.deliveryManPlus.cart.dto;

import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.cart.entity.CartMenu;
import com.deliveryManPlus.menu.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "장바구니 메뉴 요청 DTO")
@NoArgsConstructor
@Getter
public class CartMenuRequestDto {

    //옵션
    @Valid
    List<CartMenuOptionRequestDto> cartMenuOptionDtoList;

    @Schema(description = "수량", example = "1")
    //수량
    @Positive
    @NotNull
    public int quantity;

    public CartMenu toEntity(Menu menu, Cart cart) {
        return CartMenu.builder()
                .menu(menu)
                .cart(cart)
                .quantity(this.quantity)
                .build();
    }


    public List<Long> getAllCartMenuOptionDetailIdList() {
        return this.cartMenuOptionDtoList.stream()
                .flatMap(x -> x.getMenuOptionDetailIdList()
                        .stream())
                .toList();

    }

}
