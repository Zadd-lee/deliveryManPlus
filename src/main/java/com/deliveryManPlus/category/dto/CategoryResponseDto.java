package com.deliveryManPlus.category.dto;

import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.category.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CategoryResponseDto {
    @Schema(description = "카테고리 ID", example = "1")
    private final Long categoryId;

    @Schema(description = "카테고리 이름", example = "한식")
    private final String name;

    @Schema(description = "카테고리 상태", example = "ACTIVE")
    private final Status status;

    @Schema(description = "생성 일자", example = "2023-01-01T00:00:00")
    private final LocalDateTime createdAt;

    @Schema(description = "수정 일자", example = "2023-01-02T00:00:00")
    private final LocalDateTime updatedAt;

    public CategoryResponseDto(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
        this.status = category.getStatus();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }
}