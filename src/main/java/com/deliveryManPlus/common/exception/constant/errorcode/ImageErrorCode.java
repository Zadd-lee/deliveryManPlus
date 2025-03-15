package com.deliveryManPlus.common.exception.constant.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ImageErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND,"이미지를 찾을 수 없습니다."),
    NOT_VALUABLE(HttpStatus.BAD_REQUEST, "삭제된 카테고리입니다."),
    IMAGE_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패하였습니다."),
    IMAGE_COUNT_EXCEED(HttpStatus.FORBIDDEN,"이미지는 최대 10장만 저장 가능합니다" );
    private final HttpStatus httpStatus;
    private final String message;
}
