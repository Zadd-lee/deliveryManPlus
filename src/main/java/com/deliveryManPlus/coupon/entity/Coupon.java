package com.deliveryManPlus.coupon.entity;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.common.entity.CreateDateEntity;
import com.deliveryManPlus.common.utils.StringUtils;
import com.deliveryManPlus.coupon.dto.CouponUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    private Integer quantity;

    private LocalDate startAt;
    private LocalDate expiredAt;


    @OneToMany(mappedBy = "coupon", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CouponBrand> couponBrandList = new ArrayList<>();

    @OneToMany(mappedBy = "coupon")
    private List<CouponUser> couponUserList = new ArrayList<>();


    @Builder
    public Coupon(String name, String code, BigDecimal discountPrice,Integer quantity, LocalDate startAt,LocalDate expiredAt) {
        this.name = name;
        this.code = code;
        this.discountPrice = discountPrice;
        this.quantity = quantity;
        this.startAt = startAt;
        this.expiredAt = expiredAt;
        this.code = StringUtils.generateRandomCode(10);
    }

    public void addCouponBrand(CouponBrand couponBrand) {
        couponBrandList.add(couponBrand);
    }

    public void updateDate(LocalDate startAt, LocalDate expiredAt) {
        this.startAt = startAt==null?this.startAt:startAt;
        this.expiredAt = expiredAt==null?this.expiredAt:expiredAt;
    }

    public void updateByDto(CouponUpdateRequestDto requestDto) {
        this.name = requestDto.getName()==null?this.name:requestDto.getName();
        this.discountPrice = requestDto.getDiscountPrice()==null?this.discountPrice:requestDto.getDiscountPrice();
    }

    public CouponUser useCoupon(User user) {
        this.quantity--;

        return CouponUser.builder()
                .coupon(this)
                .customer(user)
                .build();
    }
}
