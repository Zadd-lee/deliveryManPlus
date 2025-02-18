package com.deliveryManPlus.repository;

import com.deliveryManPlus.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.customer.id = :userId")
    List<Order> findByCustomerId(Long userId);

    @Query("select o from Order o where o.shop.id = :shopId")
    List<Order> findByShopId(Long shopId);
}
