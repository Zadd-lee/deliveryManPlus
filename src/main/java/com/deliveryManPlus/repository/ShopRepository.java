package com.deliveryManPlus.repository;

import com.deliveryManPlus.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop,Long> {
    @Query("SELECT s FROM Shop s WHERE s.category.categoryId = :categoryId AND s.status != 'CLOSED_DOWN'")
    List<Shop> findAll(@Param("categoryId") Long categoryId);




    Optional<Shop> findByRegistNumber(String registNumber);
}
