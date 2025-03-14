package com.deliveryManPlus.auth.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum AuthenticationScheme {
    BEARER("Bearer");

    private final String name;

    /**
     * Authorization 헤더의 값으로 사용될 prefix를 생성.
     *
     * @param authenticationScheme {@link AuthenticationScheme}
     * @return 생성된 prefix
     */
    public static String generateType(AuthenticationScheme authenticationScheme) {
        return authenticationScheme.getName() + " ";
    }
}
