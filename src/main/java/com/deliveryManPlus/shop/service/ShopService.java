package com.deliveryManPlus.shop.service;

import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.shop.dto.*;
import jakarta.validation.Valid;

import java.util.List;

public interface ShopService {
    void create(User userId, @Valid ShopCreateRequestDto dto);

    List<ShopResponseDto> findAll(ShopSearchOptionDto dto);

    ShopDetailResponseDto findById(Long shopId);

    ShopDetailResponseDto updateShop(Long shopId, ShopUpdateRequestDto dto);

    ShopDetailResponseDto updateShopStatus(Long shopId, @Valid ShopStatus status);

    void deleteShop(Long shopId);

}
