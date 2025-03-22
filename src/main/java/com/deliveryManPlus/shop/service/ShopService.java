package com.deliveryManPlus.shop.service;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopService {
    void create(User userId, @Valid ShopCreateRequestDto dto, List<MultipartFile> image);

    Page<ShopResponseDto> findAll(ShopSearchOptionDto dto, int page, int size);

    ShopDetailResponseDto findById(Long shopId);

    void updateShop(Long shopId, ShopUpdateRequestDto dto, List<MultipartFile> imageList);

    void updateShopStatus(Long shopId, @Valid ShopStatus status);

    void deleteShop(Long shopId);

}
