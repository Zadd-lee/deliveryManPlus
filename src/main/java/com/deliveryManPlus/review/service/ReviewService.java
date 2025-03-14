package com.deliveryManPlus.review.service;

import com.deliveryManPlus.review.dto.ReviewCreateRequestDto;
import com.deliveryManPlus.review.dto.ReviewForCustomerResponseDto;
import com.deliveryManPlus.review.dto.ReviewForShopResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    void createReview(Long orderId, @Valid ReviewCreateRequestDto dto);

    Page<ReviewForCustomerResponseDto> getReviewForUserList(Pageable pageable);

    Page<ReviewForShopResponseDto> getReviewForShopList(Pageable pageable, Long shopId);
}
