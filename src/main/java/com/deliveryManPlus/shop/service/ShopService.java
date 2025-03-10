package com.deliveryManPlus.shop.service;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface ShopService {
    void create(User userId, @Valid ShopCreateRequestDto dto);

    Page<ShopResponseDto> findAll(ShopSearchOptionDto dto, int page, int size);

    ShopDetailResponseDto findById(Long shopId);

    ShopDetailResponseDto updateShop(Long shopId, ShopUpdateRequestDto dto);

    ShopDetailResponseDto updateShopStatus(Long shopId, @Valid ShopStatus status);

    void deleteShop(Long shopId);

}
