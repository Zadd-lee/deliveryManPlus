package com.deliveryManPlus.menu.service.imp;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.common.exception.constant.MenuErrorCode;
import com.deliveryManPlus.common.exception.constant.SessionErrorCode;
import com.deliveryManPlus.common.exception.constant.ShopErrorCode;
import com.deliveryManPlus.common.exception.exception.ApiException;
import com.deliveryManPlus.menu.model.dto.MenuCreateRequestDto;
import com.deliveryManPlus.menu.model.dto.MenuUpdateRequestDto;
import com.deliveryManPlus.menu.model.dto.MenuUpdateStatusRequestDto;
import com.deliveryManPlus.menu.model.entity.Menu;
import com.deliveryManPlus.menu.repository.MenuRepository;
import com.deliveryManPlus.menu.service.MenuService;
import com.deliveryManPlus.shop.model.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import com.deliveryManPlus.user.model.entity.User;
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
    public void create(Authentication auth, Long shopId, MenuCreateRequestDto dto) {
        //검증
        User user = userRepository.findById(auth.getId())
                .orElseThrow(() -> new ApiException(SessionErrorCode.NOT_ALLOWED));
        //todo 폐업 제외 검색 리팩토링 진행할 것.
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        validateShopAndUser(shop, user);

        Menu menu = dto.toEntity();
        menu.updateShop(shop);

        menuRepository.save(menu);

    }

    @Override
    public void update(Authentication auth, Long shopId, Long menuId, @Valid MenuUpdateRequestDto dto) {
        //검증
        User user = userRepository.findById(auth.getId())
                .orElseThrow(() -> new ApiException(SessionErrorCode.NOT_ALLOWED));
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));

        validateShopAndMenu(menu, shop);
        validateShopAndUser(shop, user);

        menu.updateByDto(dto);

    }

    @Override
    public void updateStatus(Authentication auth, Long shopId, Long menuId, MenuUpdateStatusRequestDto dto) {
        //검증
        User user = userRepository.findById(auth.getId())
                .orElseThrow(() -> new ApiException(SessionErrorCode.NOT_ALLOWED));
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));

        validateShopAndMenu(menu, shop);
        validateShopAndUser(shop, user);

        menu.updateStatus(dto.getStatus());
    }

    private static void validateShopAndMenu(Menu menu, Shop shop) {
        if (!menu.getShop().equals(shop)) {
            throw new ApiException(MenuErrorCode.NOT_FOUND);
        }
    }

    private static void validateShopAndUser(Shop shop, User user) {
        if (isNotOwner(shop, user)) {
            throw new ApiException(MenuErrorCode.UNAUTHORIZED);
        }
    }

    private static boolean isNotOwner(Shop shop, User user) {
        return !shop.getOwner().equals(user);
    }
}
