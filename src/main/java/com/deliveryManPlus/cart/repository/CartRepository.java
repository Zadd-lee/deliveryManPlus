package com.deliveryManPlus.cart.repository;

import com.deliveryManPlus.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> getCartsByCustomerId(Long id);

    @Modifying
    @Query("delete from Cart c where c.customer.id = :userId")
    void deleteByCustomerId(@Param("userId") Long userId);
}
