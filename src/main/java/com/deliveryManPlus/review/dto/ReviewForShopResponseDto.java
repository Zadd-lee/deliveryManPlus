package com.deliveryManPlus.review.dto;

import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.review.entity.Review;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewForShopResponseDto {
    private final Long reviewId;
    private final String content;
    private final Integer score;

    private final Long customerId;
    private final String customerName;

    private final Integer customerReviewQuantity;
    private final double customerReviewScoreAvg;

    private final String imagePath;

    public ReviewForShopResponseDto(Review review, Integer customerReviewQuantity , double customerReviewScoreAvg, List<Image> imageList) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.score = review.getScore();
        this.customerId = review.getCustomer().getId();
        this.customerName = review.getCustomer().getNickname();
        this.customerReviewQuantity = customerReviewQuantity;
        this.customerReviewScoreAvg = customerReviewScoreAvg;
        this.imagePath = imageList.stream()
                .filter(image -> image.getImageTarget().getTargetId().equals(review.getId()))
                .map(Image::getPath)
                .findFirst()
                .orElseGet(() -> "");
    }
}
