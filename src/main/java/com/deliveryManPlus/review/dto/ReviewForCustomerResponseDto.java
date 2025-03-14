package com.deliveryManPlus.review.dto;

import com.deliveryManPlus.review.entity.Review;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ReviewForCustomerResponseDto {
    private final Long reviewId;
    private final String content;
    private final Integer score;

    private final Long shopId;
    private final String shopName;

    private final Long orderId;
    private final String orderedAt;

    public ReviewForCustomerResponseDto(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.score = review.getScore();
        this.shopId = review.getShop().getId();
        this.shopName = review.getShop().getName();
        this.orderId = review.getOrder().getId();
        this.orderedAt = review.getOrder().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
