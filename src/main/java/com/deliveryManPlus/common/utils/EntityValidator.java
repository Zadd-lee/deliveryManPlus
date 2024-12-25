package com.deliveryManPlus.common.utils;

import com.deliveryManPlus.menu.model.entity.Menu;
import com.deliveryManPlus.order.model.entity.Order;
import com.deliveryManPlus.shop.model.entity.Shop;

public class EntityValidator {

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
