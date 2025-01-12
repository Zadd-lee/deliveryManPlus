package com.deliveryManPlus.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SessionErrorCode implements ErrorCode{
    NO_SESSION(HttpStatus.UNAUTHORIZED, "세션이 없습니다"),
    NOT_ALLOWED(HttpStatus.NOT_ACCEPTABLE,"잘못된 접근입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
