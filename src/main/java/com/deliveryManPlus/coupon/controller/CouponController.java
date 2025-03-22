package com.deliveryManPlus.coupon.controller;

import com.deliveryManPlus.coupon.dto.CouponCreateRequestDto;
import com.deliveryManPlus.coupon.dto.CouponResponseDto;
import com.deliveryManPlus.coupon.dto.CouponUpdateDateRequestDto;
import com.deliveryManPlus.coupon.dto.CouponUpdateRequestDto;
import com.deliveryManPlus.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Coupon", description = "쿠폰 API")
@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @Operation(summary = "쿠폰 생성", description = "쿠폰을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "쿠폰 생성 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "400", description = "쿠폰 시작일은 오늘 이후여야 합니다."),
                    @ApiResponse(responseCode = "400", description = "쿠폰 종료일은 시작일 이후여야 합니다."),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    @PostMapping("/admin/coupon")
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CouponCreateRequestDto requestDto) {
        couponService.createCoupon(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "쿠폰 목록 조회", description = "쿠폰 목록을 조회합니다.",
            parameters = {
                    @Parameter(name = "page", description = "페이지 번호", required = true),
                    @Parameter(name = "size", description = "페이지 사이즈", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 성공")
            })
    @GetMapping("/admin/coupon")
    public ResponseEntity<Page<CouponResponseDto>> getCouponList(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(couponService.getCouponList(page, size), HttpStatus.OK);
    }

    @Operation(summary = "쿠폰 만료 날짜 수정", description = "쿠폰의 만료 날짜를 수정합니다.",
            parameters = {
                    @Parameter(name = "couponId", description = "쿠폰 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "쿠폰 만료 날짜 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "400", description = "쿠폰 시작일은 오늘 이후여야 합니다."),
                    @ApiResponse(responseCode = "400", description = "쿠폰 종료일은 시작일 이후여야 합니다."),
                    @ApiResponse(responseCode = "400", description = "쿠폰 실행 시작 이후 수정할 수 없습니다"),
                    @ApiResponse(responseCode = "400", description = "쿠폰 종료 이후 수정할 수 없습니다."),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    @PatchMapping("/admin/coupon/{couponId}")
    public ResponseEntity<Void> updateDateCoupon(@PathVariable(name = "couponId") Long couponId, @Valid @RequestBody CouponUpdateDateRequestDto requestDto) {
        couponService.updateCouponDate(couponId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "쿠폰 수정", description = "쿠폰을 수정합니다.",
            parameters = {
                    @Parameter(name = "couponId", description = "쿠폰 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "쿠폰 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            })
    @PutMapping("/admin/coupon/{couponId}")
    public ResponseEntity<Void> updateCoupon(@PathVariable(name = "couponId") Long couponId,
                                             @Valid @RequestBody CouponUpdateRequestDto requestDto) {
        couponService.updateCoupon(couponId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "쿠폰 삭제", description = "쿠폰을 삭제합니다.",
            parameters = {
                    @Parameter(name = "couponId", description = "쿠폰 ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "쿠폰 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 쿠폰"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    @DeleteMapping("/admin/coupon/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable(name = "couponId") Long couponId) {
        couponService.deleteCoupon(couponId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "쿠폰 발급", description = "쿠폰을 발급합니다.",
            parameters = {
                    @Parameter(name = "couponCode", description = "쿠폰 코드", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "쿠폰 사용 성공"),
                    @ApiResponse(responseCode = "400", description = "이미 발긊한 쿠폰"),
                    @ApiResponse(responseCode = "400", description = "쿠폰이 모두 소진되었습니다"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 쿠폰"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    @PostMapping("/user/coupon/{couponCode}")
    public ResponseEntity<Void> useCoupon(@PathVariable(name = "couponCode") String couponCode) {
        couponService.useCoupon(couponCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}