package com.deliveryManPlus.menu.service.imp;

import com.deliveryManPlus.common.exception.constant.MenuErrorCode;
import com.deliveryManPlus.common.exception.constant.ShopErrorCode;
import com.deliveryManPlus.common.exception.exception.ApiException;
import com.deliveryManPlus.menu.constant.MenuStatus;
import com.deliveryManPlus.menu.model.dto.MenuCreateRequestDto;
import com.deliveryManPlus.menu.model.dto.MenuUpdateRequestDto;
import com.deliveryManPlus.menu.model.dto.MenuUpdateStatusRequestDto;
import com.deliveryManPlus.menu.model.entity.Menu;
import com.deliveryManPlus.menu.repository.MenuRepository;
import com.deliveryManPlus.menu.service.MenuService;
import com.deliveryManPlus.shop.model.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import com.deliveryManPlus.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImp implements MenuService {
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    @Override
    public void create(Long shopId, MenuCreateRequestDto dto) {
        //검증
        //todo 폐업 제외 검색 리팩토링 진행할 것.
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));


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
