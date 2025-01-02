package com.deliveryManPlus.order.repository;

import com.deliveryManPlus.order.model.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu,Long> {
}
