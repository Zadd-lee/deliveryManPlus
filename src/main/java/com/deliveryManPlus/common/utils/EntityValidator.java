package com.deliveryManPlus.common.utils;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.MenuErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.ShopErrorCode;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.order.entity.Order;
import com.deliveryManPlus.shop.entity.Shop;

public class EntityValidator {

    public static void validate(Shop shop) {
        User owner = SecurityUtils.getUser();
        if (!isValid(owner.getId(), shop)) {
            throw new ApiException(ShopErrorCode.FORBIDDEN);
        }
    }

    public static void validate(Menu menu, Shop shop){
        if(!isValid(shop.getId(), menu)){
            throw new ApiException(MenuErrorCode.NOT_FOUND);
        }
    }



    /**
     * 사용자가 가게의 소유자인지 확인
     * @param userId
     * @param shop
     * @return
     */
    public static boolean isValid(Long userId, Shop shop) {
        return shop.getOwner().getId().equals(userId);
    }

    /**
     * 메뉴가 가게의 메뉴인지 확인
     * @param shopId
     * @param menu
     * @return
     */
    public static boolean isValid(Long shopId, Menu menu) {
        return menu.getShop().getId().equals(shopId);
    }

    public static boolean isValid(Long shopId, Order order) {
        return order.getShop().getId().equals(shopId);
    }

    public static boolean isValid(Long shopId, Cart cart) {
        //todo 연관관계 너무 길어짐 관련 사항 리팩토링 필요
        return cart.getCartMenuList().stream()
                .findFirst()
                .map(cartMenu -> cartMenu.getMenu().getShop().getId().equals(shopId))
                .orElse(true);
    }
}
