package com.deliveryManPlus.menu.repository;

import com.deliveryManPlus.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Long> {
}
