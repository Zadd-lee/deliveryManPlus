package com.deliveryManPlus.coupon.entity;

import com.deliveryManPlus.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CouponBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public CouponBrand(Coupon coupon, Shop shop) {
        this.coupon = coupon;
        this.shop = shop;
    }
}
