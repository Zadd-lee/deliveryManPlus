package com.deliveryManPlus.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "orderd_menu")
public class OrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "menuhistory_id")
    private MenuHistory menuHistory;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public OrderMenu(MenuHistory menuHistory) {
        this.menuHistory = menuHistory;
        this.quantity = 1;
    }
    public void updateOrder(Order order) {
        this.order = order;
    }
}
