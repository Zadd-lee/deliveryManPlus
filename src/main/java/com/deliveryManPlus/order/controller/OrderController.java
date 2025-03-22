package com.deliveryManPlus.order.controller;

import com.deliveryManPlus.order.dto.*;
import com.deliveryManPlus.order.service.OrderService;
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

@Tag(name = "Order", description = "주문 API")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "주문을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "주문 생성 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "400", description = "장바구니가 비어있습니다"),
                    @ApiResponse(responseCode = "400", description = "쿠폰 할인 조건을 만족하지 못했습니다"),
                    @ApiResponse(responseCode = "404", description = "쿠폰을 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            })
    @PostMapping("/user/order")
    public ResponseEntity<Void> createOrder(@RequestBody OrderCreateRequestDto dto) {
        orderService.createOrder(dto.getCouponId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "사용자 주문 목록 조회", description = "사용자의 주문 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            },
            parameters = {
                    @Parameter(name = "page", description = "페이지 번호", required = true, example = "0"),
                    @Parameter(name = "size", description = "페이지 사이즈", required = true, example = "10")
            })
    @GetMapping("/user/order")
    public ResponseEntity<Page<OrderSimpleResponseDto>> findAllOrderForUser(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                            @RequestParam(name = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(orderService.findAllOrderForUser(page, size), HttpStatus.OK);
    }

    @Operation(summary = "사용자 주문 조회", description = "사용자의 주문을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주문 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "403", description = "권한이 없습니다"),
                    @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            },
            parameters = {
                    @Parameter(name = "orderId", description = "주문 ID", required = true, example = "1")
            })
    @GetMapping("/user/order/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> findOrderForUser(@PathVariable(name = "orderId") Long orderId) {
        return new ResponseEntity<>(orderService.findOrderForUser(orderId), HttpStatus.OK);
    }

    @Operation(summary = "식당 주문 목록 조회", description = "해당 식당의 주문 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            },
            parameters = {
                    @Parameter(name = "shopId", description = "식당 ID", required = true, example = "1"),
                    @Parameter(name = "page", description = "페이지 번호", required = true, example = "0"),
                    @Parameter(name = "size", description = "페이지 사이즈", required = true, example = "10")
            })
    @GetMapping("/owner/{shopId}/order")
    public ResponseEntity<Page<OrderDetailResponseDto>> findOrderForOwner(@PathVariable(name = "shopId") Long shopId,
                                                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                                                          @RequestParam(name = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(orderService.findOrderForOwner(shopId, page, size), HttpStatus.OK);
    }

    @Operation(summary = "주문 상태 변경", description = "주문의 상태를 변경합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주문 상태 변경 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            },
            parameters = {
                    @Parameter(name = "shopId", description = "식당 ID", required = true, example = "1"),
                    @Parameter(name = "orderId", description = "주문 ID", required = true, example = "1")
            })
    @PutMapping("/owner/{shopId}/order/{orderId}")
    public ResponseEntity<Void> updateStatus(@PathVariable(name = "shopId") Long shopId,
                                             @PathVariable(name = "orderId") Long orderId,
                                             @Valid @RequestBody OrderStatusUpdateDto dto) {
        orderService.updateStatus(shopId, orderId, dto.getStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "주문 거절", description = "주문을 거절합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "주문 거절 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            },
            parameters = {
                    @Parameter(name = "shopId", description = "식당 ID", required = true, example = "1"),
                    @Parameter(name = "orderId", description = "주문 ID", required = true, example = "1")
            })
    @DeleteMapping("/owner/{shopId}/order/{orderId}")
    public ResponseEntity<Void> reject(@PathVariable(name = "shopId") Long shopId,
                                       @PathVariable(name = "orderId") Long orderId,
                                       @Valid @RequestBody OrderStatusRejectDto dto) {
        orderService.reject(shopId, orderId, dto.getRejectReason());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}