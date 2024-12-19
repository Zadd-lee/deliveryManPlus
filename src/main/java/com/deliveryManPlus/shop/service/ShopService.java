package com.deliveryManPlus.shop.service;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.model.dto.CreateRequestDto;
import com.deliveryManPlus.shop.model.dto.ShopDetailResponseDto;
import com.deliveryManPlus.shop.model.dto.ShopResponseDto;
import com.deliveryManPlus.shop.model.dto.UpdateRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ShopService {
    void create(Authentication auth, @Valid CreateRequestDto dto);

    List<ShopResponseDto> findAll();

    ShopResponseDto findById(Long shopId);

    ShopDetailResponseDto updateShop(Long shopId, Authentication auth, UpdateRequestDto dto);

    ShopDetailResponseDto updateShopStatus(Long shopId, Authentication auth, @Valid ShopStatus status);
}
