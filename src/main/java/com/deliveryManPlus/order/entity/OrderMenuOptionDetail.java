package com.deliveryManPlus.order.entity;

import com.deliveryManPlus.cart.entity.CartMenuOptionDetail;
import com.deliveryManPlus.common.entity.CreateDateEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
public class OrderMenuOptionDetail extends CreateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "order_menu_id")
    private OrderMenu orderMenu;

    @Builder
    public OrderMenuOptionDetail(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public OrderMenuOptionDetail(CartMenuOptionDetail cartMenuOptionDetail) {
        this.name = cartMenuOptionDetail.getMenuOptionDetail().getName();
        this.price = cartMenuOptionDetail.getMenuOptionDetail().getOptionPrice();
    }

    public void updateOrderMenu(OrderMenu orderMenu) {
        this.orderMenu = orderMenu;
    }
}
