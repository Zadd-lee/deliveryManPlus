package com.deliveryManPlus.review.controller;

import com.deliveryManPlus.review.dto.ReviewCreateRequestDto;
import com.deliveryManPlus.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/user/order/{orderId}/review")
    public ResponseEntity<Void> createReview(@PathVariable(name = "orderId") Long orderId, @Valid @RequestBody ReviewCreateRequestDto dto) {
        reviewService.createReview(orderId, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
