package com.deliveryManPlus.cart.entity;

import com.deliveryManPlus.menu.entity.MenuOptionDetail;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CartMenuOptionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_menu_id")
    private CartMenu cartMenu;

    @ManyToOne
    @JoinColumn(name = "menu_option_detail_id")
    private MenuOptionDetail menuOptionDetail;

    @Builder
    public CartMenuOptionDetail(CartMenu cartMenu, MenuOptionDetail menuOptionDetail) {
        this.cartMenu = cartMenu;
        this.menuOptionDetail = menuOptionDetail;
    }
}
