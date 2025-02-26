package com.deliveryManPlus.repository;

import com.deliveryManPlus.constant.error.CategoryErrorCode;
import com.deliveryManPlus.entity.Category;
import com.deliveryManPlus.exception.ApiException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByName(String name);

    default Category findByIdOrElseThrows(Long categoryId){
        return findById(categoryId).orElseThrow(() -> new ApiException(CategoryErrorCode.NOT_FOUND));
    }
}
