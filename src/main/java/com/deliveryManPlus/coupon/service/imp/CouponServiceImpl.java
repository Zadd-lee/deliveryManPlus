package com.deliveryManPlus.coupon.service.imp;

import com.deliveryManPlus.coupon.dto.CouponCreateRequestDto;
import com.deliveryManPlus.coupon.entity.Coupon;
import com.deliveryManPlus.coupon.entity.CouponBrand;
import com.deliveryManPlus.coupon.repository.CouponBrandRepository;
import com.deliveryManPlus.coupon.repository.CouponRepository;
import com.deliveryManPlus.coupon.service.CouponService;
import com.deliveryManPlus.shop.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final CouponBrandRepository couponBrandRepository;
    private final ShopRepository shopRepository;
    @Transactional
    @Override
    public void createCoupon(CouponCreateRequestDto dto) {
        Coupon coupon = dto.toEntity();

        //특정 브랜드 지정 쿠폰일 경우, 쿠폰과 브랜드 연결
        if (dto.getShopId() != null) {
            Shop shop = shopRepository.findByIdOrElseThrows(dto.getShopId());

            CouponBrand couponBrand = CouponBrand.builder()
                    .coupon(coupon)
                    .shop(shop)
                    .build();

            coupon.addCouponBrand(couponBrand);
            couponBrandRepository.save(couponBrand);
        }
        couponRepository.save(coupon);
    }
}
