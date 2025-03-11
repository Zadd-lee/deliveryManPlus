package com.deliveryManPlus.coupon.service;

import com.deliveryManPlus.coupon.dto.CouponCreateRequestDto;
import com.deliveryManPlus.coupon.dto.CouponResponseDto;
import org.springframework.data.domain.Page;

public interface CouponService {
    void createCoupon(CouponCreateRequestDto requestDto);

    Page<CouponResponseDto> getCouponList(int page, int size);
}
