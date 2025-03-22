package com.deliveryManPlus.review.controller;

import com.deliveryManPlus.review.dto.ReviewCreateRequestDto;
import com.deliveryManPlus.review.dto.ReviewForCustomerResponseDto;
import com.deliveryManPlus.review.dto.ReviewForShopResponseDto;
import com.deliveryManPlus.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Review", description = "리뷰 API")
@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다",
            responses = {
                    @ApiResponse(responseCode = "201", description = "리뷰 생성 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "406", description = "권한 없음"),
                    @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            },
            parameters = {
                    @Parameter(name = "orderId", description = "주문 ID", required = true, example = "1")
            })
    @PostMapping("/user/order/{orderId}/review")
    public ResponseEntity<Void> createReview(@PathVariable(name = "orderId") Long orderId,
                                             @RequestPart("imageList") List<MultipartFile> imageList,
                                             @Valid @RequestPart ReviewCreateRequestDto dto) {
        reviewService.createReview(orderId, dto, imageList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "사용자 리뷰 목록 조회", description = "사용자의 리뷰 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "리뷰 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    @GetMapping("/user/review")
    public ResponseEntity<Page<ReviewForCustomerResponseDto>> getReviewForUserList(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(reviewService.getReviewForUserList(pageable), HttpStatus.OK);
    }

    @Operation(summary = "식당 리뷰 목록 조회", description = "해당 식당의 리뷰 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "리뷰 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            },
            parameters = {
                    @Parameter(name = "shopId", description = "식당 ID", required = true, example = "1")
            })
    @GetMapping("/shop/{shopId}/review")
    public ResponseEntity<Page<ReviewForShopResponseDto>> getReviewForShopList(@PageableDefault Pageable pageable,
                                                                               @PathVariable Long shopId) {
        return new ResponseEntity<>(reviewService.getReviewForShopList(pageable, shopId), HttpStatus.OK);
    }

    @Operation(summary = "리뷰 조회", description = "리뷰를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "리뷰 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            },
            parameters = {
                    @Parameter(name = "reviewId", description = "리뷰 ID", required = true, example = "1")
            })
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewForCustomerResponseDto> getReview(@PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "리뷰 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음"),
                    @ApiResponse(responseCode = "406", description = "권한 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            },
            parameters = {
                    @Parameter(name = "reviewId", description = "리뷰 ID", required = true, example = "1")
            })
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}