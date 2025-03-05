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

import java.util.Comparator;
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
        //user validate
        User user = SecurityUtils.getUser();
        //shop validate
        if (!shopRepository.existsById(shopId)) {
            throw new ApiException(ShopErrorCode.NOT_FOUND);
        }
        //menu validate
        Menu menu = menuRepository.findUseMenuByIdOrElseThrows(menuId);

        //dto validate
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

        //cart가 여러개 일 경우 최신 cart, 없을 경우 새로운 cart 생성후 db에 저장
        Cart cart = cartRepository.getCartsByCustomerId(user.getId()).stream()
                .max(Comparator.comparing(Cart::getCreatedAt))
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .customer(user)
                            .build();
                    cartRepository.save(newCart);
                    return newCart;
                });

        //menu의 shop 과 새로 생성되는 menu의 shop이 다를 경우 cart 초기화
        validCartAndShopId(shopId, cart);

        //dto와 cart menu가 동일하다면 quantity 증가
        CartMenu newCartMenu = dto.toEntity(menu, cart
                , menuOptionDetailRepository.findAllById(dto.getAllCartMenuOptionDtoList()));


        cartMenuRepository.save(newCartMenu);
    }
    //menu의 shop 과 새로 생성되는 menu의 shop이 다를 경우 cart 초기화
    private void validCartAndShopId(Long shopId, Cart cart) {
        if (!isValid(shopId, cart)) {
            List<CartMenu> cartMenuList = cart.getCartMenuList();
            cartMenuRepository.deleteAll(cartMenuList);
            cartMenuList.clear();
        }
    }
}
