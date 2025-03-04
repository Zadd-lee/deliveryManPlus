package com.deliveryManPlus.cart.repository;

import com.deliveryManPlus.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {

    List<Cart> getCartsByCustomerId(Long id);
}
