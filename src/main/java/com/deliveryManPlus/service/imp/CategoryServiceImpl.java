package com.deliveryManPlus.service.imp;

import com.deliveryManPlus.constant.Status;
import com.deliveryManPlus.constant.error.CategoryErrorCode;
import com.deliveryManPlus.dto.category.CategoryRequestDto;
import com.deliveryManPlus.dto.category.CategoryResponseDto;
import com.deliveryManPlus.dto.category.CategorySearchRequestDto;
import com.deliveryManPlus.entity.Category;
import com.deliveryManPlus.exception.ApiException;
import com.deliveryManPlus.repository.CategoryRepository;
import com.deliveryManPlus.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void createCategory(CategoryRequestDto dto) {
        //검증
        if(categoryRepository.existsByName(dto.getName())) {
            throw new ApiException(CategoryErrorCode.DUPLICATED_NAME);
        }

        Category entity = dto.toEntity();
        categoryRepository.save(entity);
    }

    @Override
    public List<CategoryResponseDto> getCategoryList(CategorySearchRequestDto dto) {

        return categoryRepository.findAll().stream()
                .filter(category -> {
                    if (dto.isPresent()) {
                        return category.getStatus().equals(Status.USE);
                    } else {
                        return true;
                    }
                })
                .map(CategoryResponseDto::new)
                .toList();
    }

    @Transactional
    @Override
    public void updateCategory(Long categoryId, CategoryRequestDto dto) {
        //검증
        Category category = categoryRepository.findByIdOrElseThrows(categoryId);
        category.updateName(dto.getName());
    }
}
