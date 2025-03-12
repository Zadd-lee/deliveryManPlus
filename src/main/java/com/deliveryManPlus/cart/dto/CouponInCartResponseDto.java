package com.deliveryManPlus.cart.dto;

import com.deliveryManPlus.coupon.entity.Coupon;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Getter
public class CouponInCartResponseDto {

    private final Long couponId;
    private final String couponName;
    private final BigDecimal discountPrice;
    private final String expiredAt;

    public CouponInCartResponseDto(Coupon coupon) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getName();
        this.discountPrice = coupon.getDiscountPrice();
        this.expiredAt = coupon.getExpiredAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
