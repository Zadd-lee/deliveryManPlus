package com.deliveryManPlus.service.imp;

import com.deliveryManPlus.constant.ShopStatus;
import com.deliveryManPlus.constant.error.MenuErrorCode;
import com.deliveryManPlus.constant.error.ShopErrorCode;
import com.deliveryManPlus.exception.ApiException;
import com.deliveryManPlus.constant.error.MenuStatus;
import com.deliveryManPlus.dto.menu.MenuCreateRequestDto;
import com.deliveryManPlus.dto.menu.MenuUpdateRequestDto;
import com.deliveryManPlus.dto.menu.MenuUpdateStatusRequestDto;
import com.deliveryManPlus.entity.Menu;
import com.deliveryManPlus.repository.MenuRepository;
import com.deliveryManPlus.service.MenuService;
import com.deliveryManPlus.entity.Shop;
import com.deliveryManPlus.repository.ShopRepository;
import com.deliveryManPlus.utils.EntityValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImp implements MenuService {
    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;

    @Override
    public void create(Long shopId, MenuCreateRequestDto dto) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        //validation
        if(shop.getStatus().equals(ShopStatus.CLOSED_DOWN)){
            throw new ApiException(ShopErrorCode.NOT_VALUABLE);
        }

        EntityValidator.validate();


        Menu menu = dto.toEntity();
        menu.updateShop(shop);

        menuRepository.save(menu);

    }

    @Override
    public void update(Long shopId, Long menuId, @Valid MenuUpdateRequestDto dto) {
        //검증
        Shop shop = shopRepository.findById(menuId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));

        menu.updateByDto(dto);

    }

    @Override
    public void updateStatus(Long shopId, Long menuId, MenuUpdateStatusRequestDto dto) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));

        menu.updateStatus(dto.getStatus());
    }

    @Override
    public void delete(Long shopId, Long menuId) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));
        menu.updateStatus(MenuStatus.NOT_USE);
    }

}
