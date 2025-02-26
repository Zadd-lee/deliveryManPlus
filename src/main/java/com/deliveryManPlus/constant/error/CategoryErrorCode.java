package com.deliveryManPlus.constant.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CategoryErrorCode implements ErrorCode {
    DUPLICATED_NAME(HttpStatus.BAD_REQUEST,"이미 존재하는 카테고리 이름입니다"),
;
    private final HttpStatus httpStatus;
    private final String message;
}
