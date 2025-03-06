package com.deliveryManPlus.cart.service.imp;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.cart.dto.CartMenuOptionDetailRequestDto;
import com.deliveryManPlus.cart.dto.CartMenuOptionRequestDto;
import com.deliveryManPlus.cart.dto.CartMenuRequestDto;
import com.deliveryManPlus.cart.dto.CartResponseDto;
import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.cart.entity.CartMenu;
import com.deliveryManPlus.cart.entity.CartMenuOptionDetail;
import com.deliveryManPlus.cart.repository.CartMenuOptionDetailRepository;
import com.deliveryManPlus.cart.repository.CartMenuRepository;
import com.deliveryManPlus.cart.repository.CartRepository;
import com.deliveryManPlus.cart.service.CartService;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CartErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.MenuErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.MenuOptionErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.ShopErrorCode;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.menu.entity.MenuOption;
import com.deliveryManPlus.menu.repository.MenuOptionDetailRepository;
import com.deliveryManPlus.menu.repository.MenuOptionRepository;
import com.deliveryManPlus.menu.repository.MenuRepository;
import com.deliveryManPlus.shop.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.deliveryManPlus.common.utils.EntityValidator.isValid;
import static com.deliveryManPlus.common.utils.SecurityUtils.getUser;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuOptionDetailRepository menuOptionDetailRepository;
    private final CartMenuRepository cartMenuRepository;
    private final CartMenuOptionDetailRepository cartMenuOptionDetailRepository;

    @Transactional
    @Override
    public void addCartMenu(Long shopId, Long menuId, CartMenuRequestDto dto) {
        //검증
        //user validate
        User user = getUser();
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
        Cart cart = cartRepository.findByCustomerIdDesc(getUser().getId())
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .customer(user)
                            .build();
                    cartRepository.save(newCart);
                    return newCart;
                });

        //menu의 shop 과 새로 생성되는 menu의 shop이 다를 경우 cart 초기화
        validCartAndShopId(shopId, cart);

        //todo dto와 cart menu가 동일하다면 quantity 증가

        CartMenu newCartMenu = dto.toEntity(menu, cart);

        //cart menu option detail 저장
        saveMenuOptionDetail(dto, newCartMenu);

        cartMenuRepository.save(newCartMenu);
    }

    @Override
    public CartResponseDto findCartList() {


        Cart cart = cartRepository.findByCustomerIdOrElseThrow(getUser().getId());

        return new CartResponseDto(cart);

    }

    @Transactional
    @Override
    public void updateCartMenuOptionDetail(Long menuId, CartMenuOptionDetailRequestDto dto) {
        //cart 검증
        Cart cart = cartRepository.findByCustomerIdOrElseThrow(getUser().getId());
        //menu option detail
        validateMenuOptionDetail(dto, menuRepository.findByIdOrElseThrows(menuId));

        CartMenu cartMenu = cart.getCartMenuList().stream()
                .filter(x -> x.getMenu().getId().equals(menuId))
                .findFirst()
                .orElseThrow(() -> new ApiException(CartErrorCode.MENU_NOT_FOUND));

        cartMenuOptionDetailRepository.deleteAllByCartMenuId(cartMenu.getId());

        List<CartMenuOptionDetail> cartMenuOptionDetailList = menuOptionDetailRepository.findAllById(dto.getCartMenuOptionDetailIdList())
                .stream()
                .map(menuOptionDetail -> CartMenuOptionDetail.builder()
                        .menuOptionDetail(menuOptionDetail)
                        .cartMenu(cartMenu)
                        .build())
                .toList();

        cartMenuOptionDetailRepository.saveAll(cartMenuOptionDetailList);

    }

    @Transactional
    @Override
    public void updateCartMenuQuantity(Long menuId, int quantity) {
           Cart cart = cartRepository.findByCustomerIdOrElseThrow(getUser().getId());

        CartMenu cartMenu = cart.getCartMenuList().stream()
                .filter(x -> x.getMenu().getId().equals(menuId))
                .findFirst()
                .orElseThrow(() -> new ApiException(CartErrorCode.MENU_NOT_FOUND));

        cartMenu.updateQuantity(quantity);
    }

    @Transactional
    @Override
    public void deleteCartMenu(Long menuId) {
        cartMenuOptionDetailRepository.deleteByMenuIdAndCustomerId(menuId,getUser().getId());
        cartMenuRepository.deleteByMenuAndCustomerId(menuId,getUser().getId());
    }

    @Transactional
    @Override
    public void deleteCart() {
        cartMenuOptionDetailRepository.deleteByCustomerId(getUser().getId());
        cartMenuRepository.deleteByCustomerId(getUser().getId());
        cartRepository.deleteByCustomerId(getUser().getId());
    }

    private void saveMenuOptionDetail(CartMenuRequestDto dto, CartMenu cartMenu) {
        List<CartMenuOptionDetail> cartMenuOptionDetailList = menuOptionDetailRepository.findAllById(dto.getAllCartMenuOptionDetailIdList())
                .stream()
                .map(x -> new CartMenuOptionDetail(cartMenu, x))
                .toList();
        cartMenuOptionDetailRepository.saveAll(cartMenuOptionDetailList);


        cartMenu.updateMenuOptionDetailList(cartMenuOptionDetailList);
    }

    //menu의 shop 과 새로 생성되는 menu의 shop이 다를 경우 cart 초기화
    private void validCartAndShopId(Long shopId, Cart cart) {
        if (!isValid(shopId, cart)) {
            List<CartMenu> cartMenuList = cart.getCartMenuList();
            cartMenuRepository.deleteAll(cartMenuList);
            cartMenuList.clear();
        }
    }

    private static void validateMenuOptionDetail(CartMenuOptionDetailRequestDto dto, Menu menu) {
        if (dto.getCartMenuOptionDetailIdList().isEmpty()) {
            return;
        }
        Set<Long> menuOptionIdSet = menu.getMenuOptionList()
                .stream()
                .map(MenuOption::getId)
                .collect(Collectors.toSet());

        if (!menuOptionIdSet.containsAll(new HashSet<>(dto.getCartMenuOptionDetailIdList()))) {
            throw new ApiException(CartErrorCode.MENU_NOT_FOUND);
        }
    }


    private void validateDtoMenu(CartMenuRequestDto dto, Menu menu) {
        List<Long> allCartMenuOptionIdList = dto.getAllCartMenuOptionDetailIdList();
        List<Long> menuOptionIdList = dto.getCartMenuOptionDtoList()
                .stream()
                .map(CartMenuOptionRequestDto::getMenuOptionId)
                .toList();

        Set<Long> menuOptionIdSet = menu.getMenuOptionList()
                .stream()
                .map(MenuOption::getId)
                .collect(Collectors.toSet());

        validateMenuOptionIds(menuOptionIdSet, allCartMenuOptionIdList, MenuOptionErrorCode.NOT_FOUND);
        validateMenuOptionIds(menuOptionIdSet, menuOptionIdList, MenuOptionErrorCode.OPTION_DETAIL_NOT_FOUND);
    }

    private void validateMenuOptionIds(Set<Long> validOptionIds, List<Long> requestedOptionIds, MenuOptionErrorCode errorCode) {
        if (!validOptionIds.containsAll(requestedOptionIds)) {
            throw new ApiException(errorCode);
        }
    }
}
