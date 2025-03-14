package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터 오류입니다."),
    INVALID_ORDERBY_TYPE(HttpStatus.BAD_REQUEST, "잘못된 정렬 조건 입니다."),
    PARAMETER_ERROR(HttpStatus.BAD_REQUEST,"조회 정렬 조건과 정렬 방법은 필수입니다" ),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
