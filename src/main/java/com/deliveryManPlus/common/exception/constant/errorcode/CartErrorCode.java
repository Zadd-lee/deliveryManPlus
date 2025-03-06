package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CartErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니 안의 메뉴를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 입니다."),
    INVALID_UPDATE(HttpStatus.BAD_REQUEST,"장바구니 메뉴는 수량 혹은 옵션만 수정 가능합니다" );

    private final HttpStatus httpStatus;
    private final String message;
}
