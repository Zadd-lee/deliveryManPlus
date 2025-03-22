package com.deliveryManPlus.category.dto;

import com.deliveryManPlus.common.constant.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryUpdateRequestDto {
    private String name;
    private Status status;
}
