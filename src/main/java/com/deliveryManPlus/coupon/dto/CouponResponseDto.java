package com.deliveryManPlus.coupon.dto;

import com.deliveryManPlus.coupon.entity.Coupon;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Getter
public class CouponResponseDto {
    private final Long id;
    private final String name;
    private final String code;
    private final BigDecimal discountPrice;
    private final Integer quantity;
    private final String startAt;
    private final String expiredAt;

    private Long couponBrandName;

    public CouponResponseDto(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.code = coupon.getCode();
        this.discountPrice = coupon.getDiscountPrice();
        this.quantity = coupon.getQuantity();
        this.startAt = coupon.getStartAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.expiredAt = coupon.getExpiredAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.couponBrandName = (coupon.getCouponBrandList() != null && !coupon.getCouponBrandList().isEmpty())
                ? coupon.getCouponBrandList().get(0).getShop().getId(): null;
    }
}
