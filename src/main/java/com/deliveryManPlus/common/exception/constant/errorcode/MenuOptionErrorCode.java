package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum MenuOptionErrorCode implements ErrorCode {
    REQUIRED_SELECTION_LIMIT(HttpStatus.NOT_FOUND,"필수 옵션인 경우 선택 제한값 필수"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"메뉴를 찾을 수 없습니다"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN,"권한이 없습니다"),;

    private final HttpStatus httpStatus;
    private final String message;
}
