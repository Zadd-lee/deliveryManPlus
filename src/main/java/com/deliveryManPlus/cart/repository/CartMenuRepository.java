package com.deliveryManPlus.cart.repository;

import com.deliveryManPlus.cart.entity.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartMenuRepository extends JpaRepository<CartMenu,Long> {
    @Modifying
    @Query("delete from CartMenu cm where cm.menu.id = :menuId and cm.cart.customer.id = :customerId")
    void deleteByMenuAndCustomerId(@Param("menuId") Long menuId, @Param("customerId") Long customerId);

    @Modifying
    @Query("delete from CartMenu cm where cm.cart.customer.id = :customerId")
    void deleteByCustomerId(@Param("customerId") Long customerId);
}
