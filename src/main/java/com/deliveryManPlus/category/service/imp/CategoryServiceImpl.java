package com.deliveryManPlus.category.service.imp;

import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.common.exception.constant.errorcode.CategoryErrorCode;
import com.deliveryManPlus.category.dto.CategoryCreateRequestDto;
import com.deliveryManPlus.category.dto.CategoryResponseDto;
import com.deliveryManPlus.category.dto.CategorySearchRequestDto;
import com.deliveryManPlus.category.dto.CategoryUpdateRequestDto;
import com.deliveryManPlus.category.entity.Category;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.category.repository.CategoryRepository;
import com.deliveryManPlus.category.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

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
    public List<CategoryResponseDto> getCategoryList(CategorySearchRequestDto dto) {

        return categoryRepository.findAll().stream()
                .filter(getStatusPredicate(dto.getUseYn()))
                .map(CategoryResponseDto::new)
                .toList();
    }

        private Predicate<Category> getStatusPredicate(String useYn) {
            return switch (useYn) {
                case "Y" -> category -> category.getStatus() == Status.USE;
                case "N" -> category -> category.getStatus() == Status.DELETED;
                default -> category -> true;
            };
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
