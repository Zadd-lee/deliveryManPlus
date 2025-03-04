package com.deliveryManPlus.cart.repository;

import com.deliveryManPlus.cart.entity.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartMenuRepository extends JpaRepository<CartMenu,Long> {
}
