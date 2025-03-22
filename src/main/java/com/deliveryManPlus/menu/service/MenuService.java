package com.deliveryManPlus.menu.service;

import com.deliveryManPlus.menu.dto.menu.MenuCreateRequestDto;
import com.deliveryManPlus.menu.dto.menu.MenuDetailResponseDto;
import com.deliveryManPlus.menu.dto.menu.MenuUpdateRequestDto;
import com.deliveryManPlus.menu.dto.menu.MenuUpdateStatusRequestDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuService {
    void create(Long shopId, @Valid MenuCreateRequestDto dto, List<MultipartFile> imageList);

    void update(Long shopId, Long menuId, @Valid MenuUpdateRequestDto dto);

    void updateStatus(Long shopId, Long menuId, MenuUpdateStatusRequestDto dto);

    void delete(Long shopId, Long menuId);

    MenuDetailResponseDto findById(Long shopId, Long menuId);
}
