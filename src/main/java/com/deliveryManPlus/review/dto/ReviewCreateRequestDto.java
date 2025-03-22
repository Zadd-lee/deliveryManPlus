package com.deliveryManPlus.review.dto;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.order.entity.Order;
import com.deliveryManPlus.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Schema(description = "리뷰 생성 요청 DTO")
public class ReviewCreateRequestDto {
    @Schema(description = "리뷰 내용", example = "Great service!", required = true)
    @NotBlank
    private String content;

    @Schema(description = "리뷰 점수", example = "5", required = true)
    @PositiveOrZero
    private Integer score;

    public Review toEntity(Order order, User customer) {
        return Review.builder()
                .content(content)
                .score(score)
                .customer(customer)
                .shop(order.getShop())
                .order(order)
                .build();
    }
}