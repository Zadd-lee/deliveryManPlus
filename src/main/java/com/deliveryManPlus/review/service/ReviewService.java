package com.deliveryManPlus.review.service;

import com.deliveryManPlus.review.dto.ReviewCreateRequestDto;
import jakarta.validation.Valid;

public interface ReviewService {
    void createReview(Long orderId, @Valid ReviewCreateRequestDto dto);
}
