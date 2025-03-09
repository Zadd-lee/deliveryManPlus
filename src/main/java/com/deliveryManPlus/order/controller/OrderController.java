package com.deliveryManPlus.order.controller;

import com.deliveryManPlus.auth.config.UserDetailsImp;
import com.deliveryManPlus.order.dto.OrderResponseDto;
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

    @Operation(summary = "주문 조회", description = "사용자의 주문을 조회합니다."
            ,responses = {
            @ApiResponse(responseCode = "200", description = "주문 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류"),
    })
    @GetMapping("/user/order")
    public ResponseEntity<List<OrderResponseDto>> findOrderForUser(@AuthenticationPrincipal UserDetailsImp userDetailsImp) {
        List<OrderResponseDto> orderResponseDtos = orderService.findOrderForUser(userDetailsImp.getBasicAuth().getUser());
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
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
    public ResponseEntity<List<OrderResponseDto>> findOrderForOwner(@PathVariable(name = "shopId") Long shopId) {
        List<OrderResponseDto> orderResponseDtos = orderService.findOrderForOwner(shopId);
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
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
    public ResponseEntity<OrderResponseDto> updateStatus(@AuthenticationPrincipal UserDetailsImp userDetailsImp,
                                                         @PathVariable(name = "shopId") Long shopId,
                                                         @PathVariable(name = "orderId") Long orderId,
                                                         @Valid @RequestBody OrderStatusUpdateDto dto) {
        OrderResponseDto orderResponseDto = orderService.updateStatus(shopId, orderId, dto);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
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
