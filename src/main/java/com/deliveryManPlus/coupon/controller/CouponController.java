package com.deliveryManPlus.coupon.controller;

import com.deliveryManPlus.coupon.dto.CouponCreateRequestDto;
import com.deliveryManPlus.coupon.dto.CouponResponseDto;
import com.deliveryManPlus.coupon.dto.CouponUpdateDateRequestDto;
import com.deliveryManPlus.coupon.dto.CouponUpdateRequestDto;
import com.deliveryManPlus.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/admin/coupon")
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CouponCreateRequestDto requestDto) {
        couponService.createCoupon(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/admin/coupon")
    public ResponseEntity<Page<CouponResponseDto>> getCouponList(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(couponService.getCouponList(page, size), HttpStatus.OK);
    }

    @PatchMapping("/admin/coupon/{couponId}")
    public ResponseEntity<Void> updateDateCoupon(@PathVariable(name = "couponId") Long couponId, @Valid @RequestBody CouponUpdateDateRequestDto requestDto) {
        couponService.updateCouponDate(couponId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin/coupon/{couponId}")
    public ResponseEntity<Void> updateCoupon(@PathVariable(name = "couponId") Long couponId
            , @Valid @RequestBody CouponUpdateRequestDto requestDto) {
        couponService.updateCoupon(couponId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
