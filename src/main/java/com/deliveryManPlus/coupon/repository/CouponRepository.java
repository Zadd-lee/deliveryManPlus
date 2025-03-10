package com.deliveryManPlus.coupon.repository;

import com.deliveryManPlus.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
}
