package com.deliveryManPlus.dto.category;

import com.deliveryManPlus.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "카테고리 생성 요청")
@NoArgsConstructor
@Getter
public class CategoryRequestDto {
    @Schema(description = "카테고리 이름", example = "한식")
    @NotBlank
    private String name;

    public Category toEntity() {
        return new Category(name);
    }
}
