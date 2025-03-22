package com.deliveryManPlus.review.dto;

import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "상점 리뷰 응답 DTO")
public class ReviewForShopResponseDto {
    @Schema(description = "리뷰 ID", example = "1", required = true)
    private final Long reviewId;

    @Schema(description = "리뷰 내용", example = "Great service!", required = true)
    private final String content;

    @Schema(description = "리뷰 점수", example = "5", required = true)
    private final Integer score;

    @Schema(description = "고객 ID", example = "1", required = true)
    private final Long customerId;

    @Schema(description = "고객 이름", example = "John Doe", required = true)
    private final String customerName;

    @Schema(description = "고객 리뷰 수", example = "10", required = true)
    private final Integer customerReviewQuantity;

    @Schema(description = "고객 리뷰 평균 점수", example = "4.5", required = true)
    private final double customerReviewScoreAvg;

    @Schema(description = "이미지 경로", example = "/images/1.jpg", required = true)
    private final String imagePath;

    public ReviewForShopResponseDto(Review review, Integer customerReviewQuantity, double customerReviewScoreAvg, List<Image> imageList) {
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
                .orElse("");
    }
}