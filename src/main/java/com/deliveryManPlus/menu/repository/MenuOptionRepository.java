package com.deliveryManPlus.menu.repository;

import com.deliveryManPlus.common.exception.constant.errorcode.MenuOptionErrorCode;
import com.deliveryManPlus.menu.entity.MenuOption;
import com.deliveryManPlus.common.exception.ApiException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
    default MenuOption findByIdOrElseThrows(Long menuId){
        return findById(menuId).orElseThrow(() -> new ApiException(MenuOptionErrorCode.NOT_FOUND));
    }

    List<MenuOption> findAllByMenuId(Long menuId);

    boolean existsByMenuId(Long menuId);

    @Modifying
    @Query("delete from MenuOption mo where mo.id in :menuOptionIdList")
    void deleteAllByIds(List<Long> menuOptionIdList);
}
