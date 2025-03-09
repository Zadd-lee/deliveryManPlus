package com.deliveryManPlus.order.repository;

import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.OrderErrorCode;
import com.deliveryManPlus.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.customer.id = :userId")
    List<Order> findByCustomerId(@Param("userId") Long userId);

    @Query("select o from Order o where o.shop.id = :shopId")
    List<Order> findByShopId(@Param("shopId") Long shopId);


    default Order findByIdOrElseThrow(Long orderId) {
        return findById(orderId)
                .orElseThrow(() -> new ApiException(OrderErrorCode.NOT_FOUND));
    }
}
