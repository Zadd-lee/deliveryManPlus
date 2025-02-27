package com.deliveryManPlus.service;

import com.deliveryManPlus.dto.category.CategoryCreateRequestDto;
import com.deliveryManPlus.dto.category.CategoryResponseDto;
import com.deliveryManPlus.dto.category.CategorySearchRequestDto;
import com.deliveryManPlus.dto.category.CategoryUpdateRequestDto;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryCreateRequestDto dto);

    List<CategoryResponseDto> getCategoryList(CategorySearchRequestDto dto);

    void updateCategory(Long categoryId, CategoryUpdateRequestDto dto);

    void deleteCategory(Long categoryId);
}
