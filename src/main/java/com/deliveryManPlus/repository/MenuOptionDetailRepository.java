package com.deliveryManPlus.repository;

import com.deliveryManPlus.entity.MenuOptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuOptionDetailRepository extends JpaRepository<MenuOptionDetail, Long> {

    @Modifying
    @Query("delete from MenuOptionDetail mod where mod.menuOption.menuOptionId in :menuOptionIdList")
    void deleteAllByMenuOptionId(List<Long> menuOptionIdList);
}
