package com.deliveryManPlus.service;

import com.deliveryManPlus.dto.category.CategoryRequestDto;
import com.deliveryManPlus.dto.category.CategoryResponseDto;
import com.deliveryManPlus.dto.category.CategorySearchRequestDto;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryRequestDto dto);

    List<CategoryResponseDto> getCategoryList(CategorySearchRequestDto dto);

    void updateCategory(Long categoryId, CategoryRequestDto dto);
}
