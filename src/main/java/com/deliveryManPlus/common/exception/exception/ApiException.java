package com.deliveryManPlus.common.exception.exception;

import com.deliveryManPlus.common.exception.constant.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;
}
