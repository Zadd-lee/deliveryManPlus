package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ReviewErrorCode implements ErrorCode {
    ALREADY_REVIEWED(HttpStatus.BAD_REQUEST,"이미 리뷰를 작성하셨습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,"리뷰를 찾을 수 없습니다" ),
    NOT_AUTHORIZED(HttpStatus.FORBIDDEN,"작성자만 삭제 가능합니다" );


    private final HttpStatus httpStatus;
    private final String message;
}
