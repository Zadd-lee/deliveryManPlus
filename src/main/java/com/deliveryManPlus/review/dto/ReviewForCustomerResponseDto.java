package com.deliveryManPlus.review.dto;

import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Schema(description = "사용자 리뷰 응답 DTO")
public class ReviewForCustomerResponseDto {
    @Schema(description = "리뷰 ID", example = "1", required = true)
    private final Long reviewId;

    @Schema(description = "리뷰 내용", example = "Great service!", required = true)
    private final String content;

    @Schema(description = "리뷰 점수", example = "5", required = true)
    private final Integer score;

    @Schema(description = "상점 ID", example = "1", required = true)
    private final Long shopId;

    @Schema(description = "상점 이름", example = "Best Shop", required = true)
    private final String shopName;

    @Schema(description = "주문 ID", example = "1", required = true)
    private final Long orderId;

    @Schema(description = "주문 날짜", example = "2023-10-01", required = true)
    private final String orderedAt;

    @Schema(description = "이미지 경로 목록", example = "[\"/images/1.jpg\", \"/images/2.jpg\"]", required = true)
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