package com.deliveryManPlus.order.controller;

import com.deliveryManPlus.auth.config.UserDetailsImp;
import com.deliveryManPlus.order.dto.OrderDetailResponseDto;
import com.deliveryManPlus.order.dto.OrderSimpleResponseDto;
import com.deliveryManPlus.order.dto.OrderStatusRejectDto;
import com.deliveryManPlus.order.dto.OrderStatusUpdateDto;
import com.deliveryManPlus.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order", description = "주문 API")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/user/order")
    public ResponseEntity<Void> createOrder() {
        orderService.createOrder();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/order")
    public ResponseEntity<List<OrderSimpleResponseDto>> findAllOrderForUser() {
        return new ResponseEntity<>(orderService.findAllOrderForUser(), HttpStatus.OK);
    }
    @GetMapping("/user/order/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> findOrderForUser(@PathVariable(name = "orderId") Long orderId) {
        return new ResponseEntity<>(orderService.findOrderForUser(orderId), HttpStatus.OK);
    }

    @Operation(summary = "주문 조회", description = "해당 식당의 주문을 조회합니다."
            ,responses = {
            @ApiResponse(responseCode = "200", description = "주문 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류"),
    }
    ,parameters = {
            @Parameter(name = "shopId", description = "식당 식별자", required = true, example = "1")
    })
    @GetMapping("/owner/{shopId}/order")
    public ResponseEntity<List<OrderSimpleResponseDto>> findOrderForOwner(@PathVariable(name = "shopId") Long shopId) {
        List<OrderSimpleResponseDto> orderSimpleResponseDtos = orderService.findOrderForOwner(shopId);
        return new ResponseEntity<>(orderSimpleResponseDtos, HttpStatus.OK);
    }

    @Operation(summary = "주문 상태 변경", description = "주문의 상태를 변경합니다."
            ,responses = {
            @ApiResponse(responseCode = "200", description = "주문 상태 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류"),
    }
    ,parameters = {
            @Parameter(name = "shopId", description = "식당 식별자", required = true, example = "1"),
            @Parameter(name = "orderId", description = "주문 식별자", required = true, example = "1")
    })
    @PutMapping("/owner/{shopId}/order/{orderId}")
    public ResponseEntity<OrderSimpleResponseDto> updateStatus(@AuthenticationPrincipal UserDetailsImp userDetailsImp,
                                                               @PathVariable(name = "shopId") Long shopId,
                                                               @PathVariable(name = "orderId") Long orderId,
                                                               @Valid @RequestBody OrderStatusUpdateDto dto) {
        OrderSimpleResponseDto orderSimpleResponseDto = orderService.updateStatus(shopId, orderId, dto);
        return new ResponseEntity<>(orderSimpleResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "주문 거절", description = "주문을 거절합니다."
            ,responses = {
            @ApiResponse(responseCode = "200", description = "주문 거절 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류"),
    }
    ,parameters = {
            @Parameter(name = "shopId", description = "식당 식별자", required = true, example = "1"),
            @Parameter(name = "orderId", description = "주문 식별자", required = true, example = "1")
    })
    @DeleteMapping("/owner/{shopId}/order/{orderId}")
    public ResponseEntity<Void> reject(@PathVariable(name = "shopId") Long shopId,
                                       @PathVariable(name = "orderId") Long orderId,
                                       @Valid @RequestBody OrderStatusRejectDto dto) {
        orderService.reject(shopId, orderId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
