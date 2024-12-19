package com.deliveryManPlus.menu.service.imp;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.common.exception.constant.MenuErrorCode;
import com.deliveryManPlus.common.exception.constant.SessionErrorCode;
import com.deliveryManPlus.common.exception.constant.ShopErrorCode;
import com.deliveryManPlus.common.exception.exception.ApiException;
import com.deliveryManPlus.menu.model.dto.MenuCreateRequestDto;
import com.deliveryManPlus.menu.model.entity.Menu;
import com.deliveryManPlus.menu.repository.MenuRepository;
import com.deliveryManPlus.menu.service.MenuService;
import com.deliveryManPlus.shop.model.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import com.deliveryManPlus.user.model.entity.User;
import com.deliveryManPlus.user.repository.UserRepository;
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
        if (!shop.getOwner().equals(user)) {
            throw new ApiException(MenuErrorCode.UNAUTHORIZED);
        }

        Menu menu = dto.toEntity();
        menu.updateShop(shop);

        menuRepository.save(menu);

    }
}
