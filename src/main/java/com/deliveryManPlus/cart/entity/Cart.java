package com.deliveryManPlus.cart.entity;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.common.entity.CreateDateEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Cart extends CreateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @OneToMany(mappedBy = "cart")
    private List<CartMenu> cartMenuList = new ArrayList<>();

    @Builder
    public Cart(User customer) {
        this.customer = customer;
    }


}
