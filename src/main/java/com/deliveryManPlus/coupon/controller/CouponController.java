package com.deliveryManPlus.coupon.controller;

import com.deliveryManPlus.coupon.dto.CouponCreateRequestDto;
import com.deliveryManPlus.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;
    @PostMapping("/admin/coupon")
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CouponCreateRequestDto requestDto) {
        couponService.createCoupon(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
