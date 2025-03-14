package com.deliveryManPlus.order.repository;

import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.OrderErrorCode;
import com.deliveryManPlus.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.shop.id = :shopId")
    Page<Order> findByShopId(@Param("shopId") Long shopId, Pageable pageable);


    default Order findByIdOrElseThrow(Long orderId) {
        return findById(orderId)
                .orElseThrow(() -> new ApiException(OrderErrorCode.NOT_FOUND));
    }

    Page<Order> findByCustomer_Id(Long customerId, Pageable pageable);
}
