package com.deliveryManPlus.cart.dto;

import com.deliveryManPlus.coupon.entity.Coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Getter
public class CouponInCartResponseDto {

    @Schema(description = "쿠폰 ID", example = "1")
    private final Long couponId;

    @Schema(description = "쿠폰 이름", example = "10% 할인")
    private final String couponName;

    @Schema(description = "할인 금액", example = "1000.00")
    private final BigDecimal discountPrice;

    @Schema(description = "만료 날짜", example = "2023-12-31")
    private final String expiredAt;

    public CouponInCartResponseDto(Coupon coupon) {
        this.couponId = coupon.getId();
        this.couponName = coupon.getName();
        this.discountPrice = coupon.getDiscountPrice();
        this.expiredAt = coupon.getExpiredAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}