package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ReviewErrorCode implements ErrorCode {
    ALREADY_REVIEWED(HttpStatus.BAD_REQUEST,"이미 리뷰를 작성하셨습니다."),;


    private final HttpStatus httpStatus;
    private final String message;
}
