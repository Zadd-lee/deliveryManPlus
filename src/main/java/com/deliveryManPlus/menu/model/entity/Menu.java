package com.deliveryManPlus.menu.model.entity;

import com.deliveryManPlus.common.model.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.menu.constant.MenuStatus;
import com.deliveryManPlus.shop.model.entity.Shop;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String context;
    private BigDecimal price;
    private MenuStatus status;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;


    public void updateShop(Shop shop) {
        this.shop = shop;
    }


}
