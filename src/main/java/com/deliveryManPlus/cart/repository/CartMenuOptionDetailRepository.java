package com.deliveryManPlus.cart.repository;

import com.deliveryManPlus.cart.entity.CartMenuOptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartMenuOptionDetailRepository extends JpaRepository<CartMenuOptionDetail, Long> {
    @Modifying
    @Query("delete from CartMenuOptionDetail c where c.cartMenu.id = :cartMenuId")
    void deleteAllByCartMenuId(@Param("cartMenuId") Long cartMenuId);

}
