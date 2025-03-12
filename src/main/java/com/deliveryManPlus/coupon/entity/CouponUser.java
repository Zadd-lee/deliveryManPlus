package com.deliveryManPlus.coupon.entity;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.common.entity.CreateDateEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CouponUser extends CreateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean useYn;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @Builder
    public CouponUser(Coupon coupon, User customer) {
        this.coupon = coupon;
        this.customer = customer;
        this.useYn = false;
    }
}
