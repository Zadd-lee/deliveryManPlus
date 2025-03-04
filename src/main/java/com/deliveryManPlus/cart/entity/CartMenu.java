package com.deliveryManPlus.cart.entity;

import com.deliveryManPlus.common.entity.CreateDateEntity;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.menu.entity.MenuOptionDetail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "menu_option_detail_id")
    private MenuOptionDetail menuOptionDetail;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;


}
