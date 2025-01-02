package com.deliveryManPlus.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{
    NOT_FOUND(HttpStatus.NOT_FOUND,"해당 유저를 찾을 수 없습니다."),

    NOT_ALLOWED(HttpStatus.NOT_ACCEPTABLE,"잘못된 접근입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
