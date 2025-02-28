package com.deliveryManPlus.repository;

import com.deliveryManPlus.constant.error.MenuOptionErrorCode;
import com.deliveryManPlus.entity.MenuOption;
import com.deliveryManPlus.exception.ApiException;
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
    @Query("delete from MenuOption mo where mo.menuOptionId in :menuOptionIdList")
    void deleteAllByIds(List<Long> menuOptionIdList);
}
