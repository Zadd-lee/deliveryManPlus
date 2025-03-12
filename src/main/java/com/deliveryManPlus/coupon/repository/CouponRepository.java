package com.deliveryManPlus.coupon.repository;

import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CouponErrorCode;
import com.deliveryManPlus.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long> {

    default Coupon findByIdOrElseThrows(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(CouponErrorCode.NOT_FOUND));
    }

    Optional<Coupon> findByCode(String couponCode);

    default Coupon findByCodeOrElseThrows(String couponCode) {
        return findByCode(couponCode).orElseThrow(() -> new ApiException(CouponErrorCode.NOT_FOUND));
    }
}
