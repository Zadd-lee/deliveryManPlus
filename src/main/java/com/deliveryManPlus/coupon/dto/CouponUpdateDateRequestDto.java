package com.deliveryManPlus.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "쿠폰 날짜 수정 요청")
@NoArgsConstructor
@Getter
public class CouponUpdateDateRequestDto {
    @Schema(description = "시작 날짜", example = "2023-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;

    @Schema(description = "만료 날짜", example = "2023-12-31")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredAt;
}