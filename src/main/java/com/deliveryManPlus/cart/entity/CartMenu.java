package com.deliveryManPlus.cart.entity;

import com.deliveryManPlus.common.entity.CreateDateEntity;
import com.deliveryManPlus.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "cartMenu",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartMenuOptionDetail> cartMenuOptionDetailList= new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Builder
    public CartMenu(int quantity, Menu menu, Cart cart) {
        this.quantity = quantity;
        this.menu = menu;
        this.cart = cart;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateMenuOptionDetailList(List<CartMenuOptionDetail> cartMenuOptionDetailList) {
        this.cartMenuOptionDetailList.addAll(cartMenuOptionDetailList);
    }
}
