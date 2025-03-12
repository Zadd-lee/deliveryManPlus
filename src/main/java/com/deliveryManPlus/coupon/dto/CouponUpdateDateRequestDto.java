package com.deliveryManPlus.coupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CouponUpdateDateRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredAt;
}
