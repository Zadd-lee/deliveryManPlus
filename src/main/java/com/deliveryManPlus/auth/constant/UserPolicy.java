package com.deliveryManPlus.auth.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserPolicy {
    RESIGNIN_MONTHS(3*12),;

    private final int months;
}
