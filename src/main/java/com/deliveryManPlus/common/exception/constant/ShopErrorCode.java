package com.deliveryManPlus.common.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ShopErrorCode implements ErrorCode{
    NOT_FOUNT(HttpStatus.NOT_FOUND,"가게를 찾을 수 없습니다"),
    NOT_VALUABLE(HttpStatus.BAD_REQUEST, "폐업한 가게입니다"),;


    private final HttpStatus httpStatus;
    private final String message;
}
