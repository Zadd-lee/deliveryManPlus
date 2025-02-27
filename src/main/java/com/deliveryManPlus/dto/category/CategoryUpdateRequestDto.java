package com.deliveryManPlus.dto.category;

import com.deliveryManPlus.constant.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryUpdateRequestDto {
    private String name;
    private Status status;
}
