package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ShopErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND,"가게를 찾을 수 없습니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "잘못된 접근입니다."),

    DUPLICATED_SHOP(HttpStatus.NOT_ACCEPTABLE, "등록된 가게입니다"),
    NOT_VALUABLE(HttpStatus.NOT_ACCEPTABLE, "폐업한 가게입니다"),
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
