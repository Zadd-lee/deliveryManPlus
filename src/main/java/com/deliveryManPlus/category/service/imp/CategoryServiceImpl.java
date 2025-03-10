package com.deliveryManPlus.category.service.imp;

import com.deliveryManPlus.category.dto.CategoryCreateRequestDto;
import com.deliveryManPlus.category.dto.CategoryResponseDto;
import com.deliveryManPlus.category.dto.CategorySearchRequestDto;
import com.deliveryManPlus.category.dto.CategoryUpdateRequestDto;
import com.deliveryManPlus.category.entity.Category;
import com.deliveryManPlus.category.repository.CategoryRepository;
import com.deliveryManPlus.category.service.CategoryService;
import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CategoryErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<CategoryResponseDto> getCategoryList(CategorySearchRequestDto dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,dto.getSort());
        return categoryRepository.findAllByDto(pageable, dto).map(CategoryResponseDto::new);
    }


    @Transactional
    @Override
    public void updateCategory(Long categoryId, CategoryUpdateRequestDto dto) {
        //검증
        Category category = categoryRepository.findByIdOrElseThrows(categoryId);

        //todo 리팩토링 필요
        if (category.getStatus() != Status.DELETED) {
            if (dto.getName() != null) {
                category.updateName(dto.getName());
            }
        } else {
            if (dto.getStatus() != null) {
                category.updateStatus(dto.getStatus());
                if (dto.getName() != null) {
                    category.updateName(dto.getName());
                }
            } else {
                throw new ApiException(CategoryErrorCode.NOT_VALUABLE);
            }
        }
        category.updateName(dto.getName());
    }

    @Transactional
    @Override
    public void deleteCategory(Long categoryId) {
        //검증
        Category category = categoryRepository.findByIdOrElseThrows(categoryId);
        category.updateStatus(Status.DELETED);
    }
}
