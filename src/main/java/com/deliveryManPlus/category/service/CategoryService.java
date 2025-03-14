package com.deliveryManPlus.category.service;

import com.deliveryManPlus.category.dto.CategoryCreateRequestDto;
import com.deliveryManPlus.category.dto.CategoryResponseDto;
import com.deliveryManPlus.category.dto.CategorySearchRequestDto;
import com.deliveryManPlus.category.dto.CategoryUpdateRequestDto;
import org.springframework.data.domain.Page;

public interface CategoryService {
    void createCategory(CategoryCreateRequestDto dto);

    Page<CategoryResponseDto> getCategoryList(CategorySearchRequestDto dto, int page, int size);

    void updateCategory(Long categoryId, CategoryUpdateRequestDto dto);

    void deleteCategory(Long categoryId);
}
