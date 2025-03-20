package com.deliveryManPlus.coupon.repository;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CouponErrorCode;
import com.deliveryManPlus.coupon.entity.Coupon;
import com.deliveryManPlus.shop.entity.Shop;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default Coupon findByIdOrElseThrows(Long id) {
        return findById(id).orElseThrow(() -> new ApiException(CouponErrorCode.NOT_FOUND));
    }

    Optional<Coupon> findByCode(String couponCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    default Coupon findByCodeOrElseThrows(String couponCode) {
        return findByCode(couponCode).orElseThrow(() -> new ApiException(CouponErrorCode.NOT_FOUND));
    }

    @Query("select c from Coupon c inner join c.couponUserList cu inner join c.couponBrandList cb where cu.customer = :user and (cb.shop = :shop or cb.shop is null) and cu.useYn = false ")
    List<Coupon> findAvailableByCustomer(User user, Shop shop);

}
