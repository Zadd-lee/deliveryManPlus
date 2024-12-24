package com.deliveryManPlus.order.repository;

import com.deliveryManPlus.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
