package com.deliveryManPlus.category.dto;

import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.category.entity.Category;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CategoryResponseDto {
    private final Long categoryId;
    private final String name;
    private final Status status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CategoryResponseDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
        this.status = category.getStatus();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }
}
