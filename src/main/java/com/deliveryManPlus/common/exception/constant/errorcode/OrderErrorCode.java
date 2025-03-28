package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum OrderErrorCode implements ErrorCode {
    CART_EMPTY(HttpStatus.BAD_REQUEST,"장바구니가 비어있습니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"주문을 찾을 수 없습니다"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN,"권한이 없습니다"),
    INVALID_COUPON(HttpStatus.BAD_REQUEST, "쿠폰 할인 조건을 충족하지 못했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
