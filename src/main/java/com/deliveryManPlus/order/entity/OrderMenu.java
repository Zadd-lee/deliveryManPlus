package com.deliveryManPlus.order.entity;

import com.deliveryManPlus.cart.entity.CartMenu;
import com.deliveryManPlus.common.entity.CreateDateEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class OrderMenu extends CreateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;
    private BigDecimal price;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "orderMenu",cascade = CascadeType.ALL)
    private final List<OrderMenuOptionDetail> orderMenuOptionDetail= new ArrayList<>();

    @Builder
    public OrderMenu(String menuName, BigDecimal menuPrice, int quantity) {
        this.name = menuName;
        this.price = menuPrice;
        this.quantity = quantity;
    }

    public void updateOrder(Order order) {
        this.order = order;
    }

    public void updateOrderMenuOptionDetail(List<OrderMenuOptionDetail> orderMenuOptionDetailList) {
        this.orderMenuOptionDetail.addAll(orderMenuOptionDetailList);
    }

    public OrderMenu(CartMenu cartMenu) {
        this.name = cartMenu.getMenu().getName();
        this.price = cartMenu.getMenu().getPrice();
        this.quantity = cartMenu.getQuantity();
    }
}
