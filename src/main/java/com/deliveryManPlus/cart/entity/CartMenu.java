package com.deliveryManPlus.cart.entity;

import com.deliveryManPlus.common.entity.CreateDateEntity;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.menu.entity.MenuOptionDetail;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class CartMenu extends CreateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;


    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(mappedBy = "cartMenu")
    private List<MenuOptionDetail> menuOptionDetailList;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Builder
    public CartMenu(int quantity, Menu menu, List<MenuOptionDetail> menuOptionDetailList, Cart cart) {
        this.quantity = quantity;
        this.menu = menu;
        this.menuOptionDetailList = menuOptionDetailList;
        this.cart = cart;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}
