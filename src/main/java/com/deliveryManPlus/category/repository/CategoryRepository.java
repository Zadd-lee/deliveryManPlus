package com.deliveryManPlus.category.repository;

import com.deliveryManPlus.category.dto.CategorySearchRequestDto;
import com.deliveryManPlus.category.entity.Category;
import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CategoryErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByName(String name);

    default Category findByIdOrElseThrows(Long categoryId){
        return findById(categoryId).orElseThrow(() -> new ApiException(CategoryErrorCode.NOT_FOUND));
    }

    default Page<Category> findAllByDto(Pageable pageable, CategorySearchRequestDto dto) {
        return findAllByStatusInAndNameLike(dto.getStatusList(), dto.getKeywordForSQL(), pageable);
    }

    Page<Category> findAllByStatusInAndNameLike(List<Status> status, String name, Pageable pageable);
}
