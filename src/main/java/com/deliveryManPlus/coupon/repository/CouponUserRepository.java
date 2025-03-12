package com.deliveryManPlus.coupon.repository;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.coupon.entity.Coupon;
import com.deliveryManPlus.coupon.entity.CouponUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponUserRepository extends JpaRepository<CouponUser,Long> {
    boolean existsByCouponAndCustomer(Coupon coupon, User customer);
}
