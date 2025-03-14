package com.deliveryManPlus.menu.service;

import com.deliveryManPlus.menu.dto.menuOption.MenuOptionRequestDto;

import java.util.List;

public interface MenuOptionService {
    void createMenuOptions(Long shopId, Long menuId, List<MenuOptionRequestDto> dtoList);

    void deleteById(Long shopId, Long menuId);
}
