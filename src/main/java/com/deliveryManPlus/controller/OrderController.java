package com.deliveryManPlus.controller;

import com.deliveryManPlus.service.imp.UserDetailsImp;
import com.deliveryManPlus.dto.order.OrderCreateRequestDto;
import com.deliveryManPlus.dto.order.OrderResponseDto;
import com.deliveryManPlus.dto.order.OrderStatusRejectDto;
import com.deliveryManPlus.dto.order.OrderStatusUpdateDto;
import com.deliveryManPlus.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/user/order")
    public ResponseEntity<Void> createOrder(@AuthenticationPrincipal UserDetailsImp userDetailsImp,
                                            @Valid @RequestBody OrderCreateRequestDto dto) {
        orderService.createOrder(userDetailsImp.getBasicAuth().getUser(), dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/order")
    public ResponseEntity<List<OrderResponseDto>> findOrderForUser(@AuthenticationPrincipal UserDetailsImp userDetailsImp) {
        List<OrderResponseDto> orderResponseDtos = orderService.findOrderForUser(userDetailsImp.getBasicAuth().getUser());
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/owner/{shopId}/order")
    public ResponseEntity<List<OrderResponseDto>> findOrderForOwner(@PathVariable Long shopId) {
        List<OrderResponseDto> orderResponseDtos = orderService.findOrderForOwner(shopId);
        return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
    }

    @PutMapping("/owner/{shopId}/order/{orderId}")
    public ResponseEntity<OrderResponseDto> updateStatus(@AuthenticationPrincipal UserDetailsImp userDetailsImp,
                                                         @PathVariable Long shopId,
                                                         @PathVariable Long orderId,
                                                         @Valid @RequestBody OrderStatusUpdateDto dto) {
        OrderResponseDto orderResponseDto = orderService.updateStatus(shopId, orderId, dto);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/owner/{shopId}/order/{orderId}")
    public ResponseEntity<Void> reject(@PathVariable Long shopId,
                                       @PathVariable Long orderId,
                                       @Valid @RequestBody OrderStatusRejectDto dto) {
        orderService.reject(shopId, orderId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
