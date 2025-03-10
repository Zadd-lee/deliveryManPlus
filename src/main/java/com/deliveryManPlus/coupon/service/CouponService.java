package com.deliveryManPlus.coupon.service;

import com.deliveryManPlus.coupon.dto.CouponCreateRequestDto;

public interface CouponService {
    void createCoupon(CouponCreateRequestDto requestDto);
}
