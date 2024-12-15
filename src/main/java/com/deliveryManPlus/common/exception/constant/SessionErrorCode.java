package com.deliveryManPlus.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SessionErrorCode implements ErrorCode{
    NO_SESSION(HttpStatus.UNAUTHORIZED, "세션이 없습니다"),
    NOT_ALLOWED(HttpStatus.BAD_REQUEST,"잘못된 접근입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
