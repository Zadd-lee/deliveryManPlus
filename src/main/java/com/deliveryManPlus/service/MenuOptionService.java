package com.deliveryManPlus.service;

import com.deliveryManPlus.dto.menuOption.MenuOptionDto;

import java.util.List;

public interface MenuOptionService {
    void createMenuOptions(Long shopId, Long menuId, List<MenuOptionDto> dtoList);

    void deleteById(Long menuId);
}
