package com.deliveryManPlus.shop.service;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.shop.model.dto.CreateRequestDto;
import jakarta.validation.Valid;

public interface ShopService {
    void create(Authentication auth, @Valid CreateRequestDto dto);
}
