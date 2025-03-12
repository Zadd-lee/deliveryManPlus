package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CouponErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다."),
    FORBIDDEN_EDIT_START_AT(HttpStatus.FORBIDDEN, "쿠폰 실행 시작 이후 수정할 수 없습니다"),
    FORBIDDEN_EDIT_EDIT_DATE(HttpStatus.FORBIDDEN,"쿠폰 종료 이후 수정할 수 없습니다." ),
    INVALID_START_AT(HttpStatus.BAD_REQUEST, "쿠폰 시작일은 오늘 이후여야 합니다."),
    INVALID_END_AT(HttpStatus.BAD_REQUEST, "쿠폰 종료일은 시작일 이후여야 합니다."),;

   private final HttpStatus httpStatus;
   private final String message;
}
