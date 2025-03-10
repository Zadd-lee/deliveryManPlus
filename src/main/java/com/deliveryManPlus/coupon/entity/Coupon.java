package com.deliveryManPlus.coupon.entity;

import com.deliveryManPlus.common.entity.CreateDateEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Coupon extends CreateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private BigDecimal discountPrice;
    private LocalDateTime expiredAt;
    private boolean useYn;


    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CouponBrand> couponBrandList = new ArrayList<>();

}
