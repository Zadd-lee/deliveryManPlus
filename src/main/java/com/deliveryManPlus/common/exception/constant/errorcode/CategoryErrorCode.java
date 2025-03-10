package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CategoryErrorCode implements ErrorCode {
    DUPLICATED_NAME(HttpStatus.BAD_REQUEST,"이미 존재하는 카테고리 이름입니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않은 카테고리입니다."),
    NOT_VALUABLE(HttpStatus.BAD_REQUEST, "삭제된 카테고리입니다."),;
    private final HttpStatus httpStatus;
    private final String message;
}
