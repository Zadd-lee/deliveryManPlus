package com.deliveryManPlus.cart.repository;

import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CartErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.customer.id = :customerId order by c.createdAt desc limit 1")
    Optional<Cart> findByCustomerIdDesc(@Param("customerId") Long customerId);

    default Cart findByCustomerIdOrElseThrow(Long customerId) {
        return findByCustomerIdDesc(customerId)
                .orElseThrow(() -> new ApiException(CartErrorCode.NOT_FOUND));
    }

    @Modifying
    @Query("delete from Cart c where c.customer.id = :userId")
    void deleteByCustomerId(@Param("userId") Long userId);
}
