package com.deliveryManPlus.coupon.dto;

import com.deliveryManPlus.coupon.entity.Coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "쿠폰 생성 요청")
@NoArgsConstructor
@Getter
public class CouponCreateRequestDto {
    @Schema(description = "쿠폰 이름", example = "10% 할인")
    @NotBlank
    private String name;

    @Schema(description = "할인 금액", example = "1000.00")
    @NotNull
    private BigDecimal discountPrice;

    @Schema(description = "수량", example = "100")
    @Positive
    private Integer quantity;

    @Schema(description = "시작 날짜", example = "2023-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startAt;

    @Schema(description = "만료 날짜", example = "2023-12-31")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredAt;

    @Schema(description = "상점 ID", example = "1")
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