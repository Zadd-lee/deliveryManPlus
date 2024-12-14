package com.deliveryManPlus.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SessionErrorCode implements ErrorCode{
    NO_SESSION(HttpStatus.UNAUTHORIZED, "세션이 없습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
