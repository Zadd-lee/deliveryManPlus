package com.deliveryManPlus.shop.service;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.shop.model.dto.CreateRequestDto;
import com.deliveryManPlus.shop.model.dto.ShopResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ShopService {
    void create(Authentication auth, @Valid CreateRequestDto dto);

    List<ShopResponseDto> findAll();
}
