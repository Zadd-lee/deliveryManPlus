package com.deliveryManPlus.utils;

import com.deliveryManPlus.constant.error.MenuErrorCode;
import com.deliveryManPlus.constant.error.ShopErrorCode;
import com.deliveryManPlus.entity.Menu;
import com.deliveryManPlus.entity.Order;
import com.deliveryManPlus.entity.Shop;
import com.deliveryManPlus.entity.User;
import com.deliveryManPlus.exception.ApiException;

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
}
