package com.deliveryManPlus.cart.dto;

import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.coupon.entity.Coupon;
import com.deliveryManPlus.shop.entity.Shop;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
@Schema(description = "장바구니 응답 DTO")
@Getter
public class CartResponseDto {
    @Schema(description = "장바구니 ID", example = "1")
    private final Long cartId;
    @Schema(description = "가게 ID", example = "1")
    private final Long shopId;
    @Schema(description = "가게 이름", example = "금니 정식 원빈점")
    private final String shopName;

    private final List<CouponInCartResponseDto> couponInCartResponseDtoList;

    private final List<CartMenuResponseDto> cartMenuResponseDtoList;

    public CartResponseDto(Cart cart, List<Coupon> couponList) {
        this.cartId = cart.getId();
        Shop shop = cart.getCartMenuList().get(0).getMenu().getShop();
        this.shopId = shop.getId();
        this.shopName = shop.getName();
        this.cartMenuResponseDtoList = cart.getCartMenuList().stream()
                .map(CartMenuResponseDto::new)
                .toList();

        this.couponInCartResponseDtoList = couponList.stream()
                .map(CouponInCartResponseDto::new)
                .toList();
    }
}
