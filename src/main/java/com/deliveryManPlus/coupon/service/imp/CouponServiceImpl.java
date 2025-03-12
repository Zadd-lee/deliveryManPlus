package com.deliveryManPlus.coupon.service.imp;

import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CouponErrorCode;
import com.deliveryManPlus.coupon.dto.CouponCreateRequestDto;
import com.deliveryManPlus.coupon.dto.CouponResponseDto;
import com.deliveryManPlus.coupon.dto.CouponUpdateDateRequestDto;
import com.deliveryManPlus.coupon.dto.CouponUpdateRequestDto;
import com.deliveryManPlus.coupon.entity.Coupon;
import com.deliveryManPlus.coupon.entity.CouponBrand;
import com.deliveryManPlus.coupon.repository.CouponBrandRepository;
import com.deliveryManPlus.coupon.repository.CouponRepository;
import com.deliveryManPlus.coupon.service.CouponService;
import com.deliveryManPlus.shop.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final ShopRepository shopRepository;
    private final CouponBrandRepository couponBrandRepository;

    @Transactional
    @Override
    public void createCoupon(CouponCreateRequestDto dto) {
        validDate(dto.getStartAt(), dto.getExpiredAt());
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

    @Override
    public Page<CouponResponseDto> getCouponList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return couponRepository.findAll(pageable)
                .map(CouponResponseDto::new);
    }

    @Transactional
    @Override
    public void updateCouponDate(Long couponId, CouponUpdateDateRequestDto requestDto) {
        //dto 검증
        Coupon coupon = couponRepository.findByIdOrElseThrows(couponId);

        validateCouponDto(requestDto, coupon);

        coupon.updateDate(requestDto.getStartAt(), requestDto.getExpiredAt());

    }

    @Transactional
    @Override
    public void updateCoupon(Long couponId, CouponUpdateRequestDto requestDto) {
        Coupon coupon = couponRepository.findByIdOrElseThrows(couponId);
        validDate(coupon.getStartAt(), coupon.getExpiredAt());

        coupon.updateByDto(requestDto);

    }

    private static void validDate(LocalDate startAt, LocalDate expiredAt) {
        if (startAt.isBefore(LocalDate.now())) {
            throw new ApiException(CouponErrorCode.INVALID_START_AT);
        }
        if (expiredAt.isBefore(startAt)) {
            throw new ApiException(CouponErrorCode.INVALID_END_AT);
        }
    }

    private static void validateCouponDto(CouponUpdateDateRequestDto requestDto, Coupon coupon) {
        if (requestDto.getStartAt() == null && requestDto.getExpiredAt() == null) {
            throw new IllegalArgumentException("입력된 값이 없습니다");
        } else if (requestDto.getStartAt() != null && coupon.getStartAt().isBefore(LocalDate.now())) {
            throw new ApiException(CouponErrorCode.FORBIDDEN_EDIT_START_AT);
        } else if (coupon.getExpiredAt().isBefore(LocalDate.now())) {
            throw new ApiException(CouponErrorCode.FORBIDDEN_EDIT_EDIT_DATE);
        }
        validDate(requestDto.getStartAt()==null? coupon.getStartAt():requestDto.getStartAt(),
                requestDto.getExpiredAt()==null? coupon.getExpiredAt():requestDto.getExpiredAt());
    }
}
