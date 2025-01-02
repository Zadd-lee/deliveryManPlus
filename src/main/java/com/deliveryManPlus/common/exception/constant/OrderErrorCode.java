package com.deliveryManPlus.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum OrderErrorCode implements ErrorCode{
    NOT_FOUND(HttpStatus.NOT_FOUND,"주문을 찾을 수 없습니다"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN,"권한이 없습니다"),;

    private final HttpStatus httpStatus;
    private final String message;
}
