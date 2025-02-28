package com.deliveryManPlus.menu.repository;

import com.deliveryManPlus.menu.entity.MenuHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuHistoryRepository extends JpaRepository<MenuHistory,Long> {
}
