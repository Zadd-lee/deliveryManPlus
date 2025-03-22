package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    EXIST_USER(HttpStatus.CONFLICT,"이미 가입한 사용자입니다"),
    CANCELED_USER(HttpStatus.GONE,"탈퇴한 사용자입니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다"),
    PASSWORD_INCORRECT(HttpStatus.FORBIDDEN,"비밀번호 오류입니다" );

    private final HttpStatus httpStatus;
    private final String message;

}
