package com.deliveryManPlus.review.service;

import com.deliveryManPlus.review.dto.ReviewCreateRequestDto;
import com.deliveryManPlus.review.dto.ReviewForCustomerResponseDto;
import com.deliveryManPlus.review.dto.ReviewForShopResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    void createReview(Long orderId, @Valid ReviewCreateRequestDto dto, List<MultipartFile> imageList);

    Page<ReviewForCustomerResponseDto> getReviewForUserList(Pageable pageable);

    Page<ReviewForShopResponseDto> getReviewForShopList(Pageable pageable, Long shopId);

    ReviewForCustomerResponseDto getReview(Long reviewId);

    void deleteReview(Long reviewId);
}
