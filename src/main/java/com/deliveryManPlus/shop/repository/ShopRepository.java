package com.deliveryManPlus.shop.repository;

import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.dto.ShopSearchOptionDto;
import com.deliveryManPlus.shop.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop,Long> {
    @Query("SELECT s FROM Shop s WHERE s.category.categoryId = :categoryId AND s.status != 'CLOSED_DOWN'")
    List<Shop> findAll(@Param("categoryId") Long categoryId);

    default Page<Shop> findAllByDto(ShopSearchOptionDto dto, Pageable pageable) {
        return findAllByCategory_CategoryIdAndNameLikeAndStatusIsNot(dto.getCategoryId(), dto.getKeywordForSQL(),ShopStatus.CLOSED_DOWN, pageable);
    }

    Optional<Shop> findByRegistNumber(String registNumber);

    Page<Shop> findAllByCategory_CategoryIdAndNameLikeAndStatusIsNot(Long categoryCategoryId, String name, ShopStatus status, Pageable pageable);
}
