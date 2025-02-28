package com.deliveryManPlus.category.repository;

import com.deliveryManPlus.common.exception.constant.errorcode.CategoryErrorCode;
import com.deliveryManPlus.category.entity.Category;
import com.deliveryManPlus.common.exception.ApiException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByName(String name);

    default Category findByIdOrElseThrows(Long categoryId){
        return findById(categoryId).orElseThrow(() -> new ApiException(CategoryErrorCode.NOT_FOUND));
    }
}
