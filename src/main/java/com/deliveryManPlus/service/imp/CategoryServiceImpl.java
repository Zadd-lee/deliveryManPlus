package com.deliveryManPlus.service.imp;

import com.deliveryManPlus.constant.error.CategoryErrorCode;
import com.deliveryManPlus.dto.category.CategoryCreateRequestDto;
import com.deliveryManPlus.entity.Category;
import com.deliveryManPlus.exception.ApiException;
import com.deliveryManPlus.repository.CategoryRepository;
import com.deliveryManPlus.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void createCategory(CategoryCreateRequestDto dto) {
        //검증
        if(categoryRepository.existsByName(dto.getName())) {
            throw new ApiException(CategoryErrorCode.DUPLICATED_NAME);
        }

        Category entity = dto.toEntity();
        categoryRepository.save(entity);
    }
}
