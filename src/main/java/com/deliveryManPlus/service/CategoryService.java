package com.deliveryManPlus.service;

import com.deliveryManPlus.dto.category.CategoryCreateRequestDto;
import com.deliveryManPlus.dto.category.CategoryResponseDto;
import com.deliveryManPlus.dto.category.CategorySearchRequestDto;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryCreateRequestDto dto);

    List<CategoryResponseDto> getCategoryList(CategorySearchRequestDto dto);
}
