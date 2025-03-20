package com.deliveryManPlus.coupon.repository;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.coupon.entity.Coupon;
import com.deliveryManPlus.coupon.entity.CouponUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponUserRepository extends JpaRepository<CouponUser, Long> {
    boolean existsByCouponAndCustomer(Coupon coupon, User customer);

    Optional<CouponUser> findByCustomerAndCouponId(User user, Long couponId);
}
