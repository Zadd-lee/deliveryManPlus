package com.deliveryManPlus.category.service;

import com.deliveryManPlus.category.dto.CategoryCreateRequestDto;
import com.deliveryManPlus.category.dto.CategoryResponseDto;
import com.deliveryManPlus.category.dto.CategorySearchRequestDto;
import com.deliveryManPlus.category.dto.CategoryUpdateRequestDto;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryCreateRequestDto dto);

    List<CategoryResponseDto> getCategoryList(CategorySearchRequestDto dto);

    void updateCategory(Long categoryId, CategoryUpdateRequestDto dto);

    void deleteCategory(Long categoryId);
}
