package com.deliveryManPlus.shop.service;

import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.model.dto.CreateRequestDto;
import com.deliveryManPlus.shop.model.dto.ShopDetailResponseDto;
import com.deliveryManPlus.shop.model.dto.ShopResponseDto;
import com.deliveryManPlus.shop.model.dto.UpdateRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ShopService {
    void create(Long userId, @Valid CreateRequestDto dto);

    List<ShopResponseDto> findAll();

    ShopDetailResponseDto findById(Long shopId);

    ShopDetailResponseDto updateShop(Long shopId, Long userId, UpdateRequestDto dto);

    ShopDetailResponseDto updateShopStatus(Long shopId, Long userId, @Valid ShopStatus status);

    void deleteShop(Long shopId, Long userId);

}
