package com.deliveryManPlus.review.controller;

import com.deliveryManPlus.review.dto.ReviewCreateRequestDto;
import com.deliveryManPlus.review.dto.ReviewForCustomerResponseDto;
import com.deliveryManPlus.review.dto.ReviewForShopResponseDto;
import com.deliveryManPlus.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/user/order/{orderId}/review")
    public ResponseEntity<Void> createReview(@PathVariable(name = "orderId") Long orderId, @Valid @RequestBody ReviewCreateRequestDto dto) {
        reviewService.createReview(orderId, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/review")
    public ResponseEntity<Page<ReviewForCustomerResponseDto>> getReviewForUserList(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(reviewService.getReviewForUserList(pageable),HttpStatus.OK);
    }

    @GetMapping("/shop/{shopId}/review")
    public ResponseEntity<Page<ReviewForShopResponseDto>> getReviewForShopList(@PageableDefault Pageable pageable,
                                                                               @PathVariable Long shopId) {
        return new ResponseEntity<>(reviewService.getReviewForShopList(pageable,shopId),HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewForCustomerResponseDto> getReview(@PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewService.getReview(reviewId),HttpStatus.OK);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
