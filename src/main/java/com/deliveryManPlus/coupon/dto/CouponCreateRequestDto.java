package com.deliveryManPlus.coupon.dto;

import com.deliveryManPlus.coupon.entity.Coupon;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class CouponCreateRequestDto {
    @NotBlank
    private String name;
    @NotNull
    private BigDecimal discountPrice;

    @Positive
    private Integer quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredAt;

    private Long shopId;

    public Coupon toEntity() {
        return Coupon.builder()
                .name(name)
                .discountPrice(discountPrice)
                .quantity(quantity)
                .expiredAt(expiredAt)
                .startAt(startAt)
                .build();
    }
}
