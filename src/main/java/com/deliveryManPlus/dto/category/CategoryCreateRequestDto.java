package com.deliveryManPlus.dto.category;

import com.deliveryManPlus.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryCreateRequestDto {
    @NotBlank
    private String name;

    public Category toEntity() {
        return new Category(name);
    }
}
