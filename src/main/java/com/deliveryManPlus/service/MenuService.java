package com.deliveryManPlus.service;

import com.deliveryManPlus.dto.menu.*;
import jakarta.validation.Valid;

public interface MenuService {
    void create(Long shopId, @Valid MenuCreateRequestDto dto);

    void update(Long shopId, Long menuId, @Valid MenuUpdateRequestDto dto);

    void updateStatus(Long shopId, Long menuId, MenuUpdateStatusRequestDto dto);

    void delete(Long shopId, Long menuId);

    MenuDetailResponseDto findById(Long shopId, Long menuId);
}
