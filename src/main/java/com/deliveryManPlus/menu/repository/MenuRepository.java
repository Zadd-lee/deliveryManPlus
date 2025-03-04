package com.deliveryManPlus.menu.repository;

import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.MenuErrorCode;
import com.deliveryManPlus.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {
    default Menu findByIdOrElseThrows(Long menuId) {

        return findById(menuId).orElseThrow(()->new ApiException(MenuErrorCode.NOT_FOUND));
    }

    default Menu findUseMenuByIdOrElseThrows(Long menuId) {
        return findUseMenu(menuId).orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));
    }

    @Query("select m from Menu m where m.id = :menuId and m.status = 'USE'")
    Optional<Menu> findUseMenu(@Param("menuId") Long menuId);
}
