package com.deliveryManPlus.order.repository;

import com.deliveryManPlus.order.model.entity.MenuHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuHistoryRepository extends JpaRepository<MenuHistory,Long> {
}
