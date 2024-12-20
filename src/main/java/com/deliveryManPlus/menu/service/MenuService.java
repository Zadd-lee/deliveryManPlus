package com.deliveryManPlus.menu.service;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.menu.model.dto.MenuCreateRequestDto;
import com.deliveryManPlus.menu.model.dto.MenuUpdateRequestDto;
import jakarta.validation.Valid;

public interface MenuService {
    void create(Authentication auth, Long shopId, @Valid MenuCreateRequestDto dto);

    void update(Authentication auth, Long shopId, Long menuId, @Valid MenuUpdateRequestDto dto);
}
