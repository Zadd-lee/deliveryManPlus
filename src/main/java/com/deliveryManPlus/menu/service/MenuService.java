package com.deliveryManPlus.menu.service;

import com.deliveryManPlus.menu.model.dto.MenuCreateRequestDto;
import com.deliveryManPlus.menu.model.dto.MenuUpdateRequestDto;
import com.deliveryManPlus.menu.model.dto.MenuUpdateStatusRequestDto;
import jakarta.validation.Valid;

public interface MenuService {
    void create(Long userId, Long shopId, @Valid MenuCreateRequestDto dto);

    void update(Long userId, Long shopId, Long menuId, @Valid MenuUpdateRequestDto dto);

    void updateStatus(Long userId, Long shopId, Long menuId, MenuUpdateStatusRequestDto dto);

    void delete(Long userId, Long shopId, Long menuId);
}
