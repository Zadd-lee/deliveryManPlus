package com.deliveryManPlus.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderBy {
    CREATED_DATE("createdAt"),
    UPDATED_DATE("updatedAt");

    private final String name;
}
