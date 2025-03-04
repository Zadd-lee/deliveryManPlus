package com.deliveryManPlus.cart.service.imp;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.cart.dto.CartCreateMenuDto;
import com.deliveryManPlus.cart.dto.CartMenuOptionRequestDto;
import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.cart.entity.CartMenu;
import com.deliveryManPlus.cart.repository.CartMenuRepository;
import com.deliveryManPlus.cart.repository.CartRepository;
import com.deliveryManPlus.cart.service.CartService;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.MenuErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.ShopErrorCode;
import com.deliveryManPlus.common.utils.SecurityUtils;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.menu.repository.MenuOptionDetailRepository;
import com.deliveryManPlus.menu.repository.MenuOptionRepository;
import com.deliveryManPlus.menu.repository.MenuRepository;
import com.deliveryManPlus.shop.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.deliveryManPlus.common.utils.EntityValidator.isValid;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuOptionDetailRepository menuOptionDetailRepository;
    private final CartMenuRepository cartMenuRepository;

    @Transactional
    @Override
    public void addCartMenu(Long shopId, Long menuId, CartCreateMenuDto dto) {
        //검증
        User user = SecurityUtils.getUser();
        List<Cart> cartsByCustomerId = cartRepository.getCartsByCustomerId(user.getId());
        Cart cart = null;
        if (cartsByCustomerId.isEmpty()) {
            cart = Cart.builder()
                    .customer(user)
                    .build();
            cartRepository.save(cart);
        } else {
            cart = cartsByCustomerId.get(0);
            if (!isValid(shopId, cart)) {
                //todo list clear 시 하위 데이터 delete 되는지 확인 필요
                cart.getCartMenuList().clear();
            }
        }

        //shop validate
        if (!shopRepository.existsById(shopId)) {
            throw new ApiException(ShopErrorCode.NOT_FOUND);
        }

        //menu validate
        Menu menu = menuRepository.findUseMenuByIdOrElseThrows(menuId);

        //menuOption validate
        dto.getCartMenuOptionDtoList()
                .stream()
                .map(CartMenuOptionRequestDto::getMenuOptionId)
                .forEach(
                        menuOptionId -> {
                            if (!menuOptionRepository.existsById(menuOptionId)) {
                                throw new ApiException(MenuErrorCode.NOT_FOUND);
                            }
                        }
                );

        List<Long> cartMenuOptionDetailIdList = dto.getAllCartMenuOptionDtoList();

        CartMenu newCartMenu = dto.toEntity(menu, cart
                , menuOptionDetailRepository.findAllById(cartMenuOptionDetailIdList));


        cartMenuRepository.save(newCartMenu);
    }
}
