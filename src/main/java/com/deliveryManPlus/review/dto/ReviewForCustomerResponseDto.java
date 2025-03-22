package com.deliveryManPlus.review.dto;

import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.review.entity.Review;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class ReviewForCustomerResponseDto {
    private final Long reviewId;
    private final String content;
    private final Integer score;

    private final Long shopId;
    private final String shopName;

    private final Long orderId;
    private final String orderedAt;

    private final List<String> imagePathList;

    public ReviewForCustomerResponseDto(Review review, List<Image> imageList) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.score = review.getScore();
        this.shopId = review.getShop().getId();
        this.shopName = review.getShop().getName();
        this.orderId = review.getOrder().getId();
        this.orderedAt = review.getOrder().getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.imagePathList = imageList.stream()
                .map(Image::getPath)
                .toList();
    }
}
