package com.deliveryManPlus.coupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class CouponUpdateRequestDto {
    private String name;
    private BigDecimal discountPrice;
}
