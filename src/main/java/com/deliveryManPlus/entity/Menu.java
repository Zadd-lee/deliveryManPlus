package com.deliveryManPlus.entity;

import com.deliveryManPlus.constant.error.MenuStatus;
import com.deliveryManPlus.dto.menu.MenuUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Menu extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String context;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public Menu(String name, String context, BigDecimal price, MenuStatus status, Shop shop) {
        this.name = name;
        this.context = context;
        this.price = price;
        this.status = status;
    }

    public void updateShop(Shop shop) {
        this.shop = shop;
    }


    public void updateByDto(MenuUpdateRequestDto dto) {
        this.name = dto.getName();
        this.context = dto.getContext();
        this.price = dto.getPrice();

    }

    public void updateStatus(MenuStatus status) {
        this.status = status;
    }
}
