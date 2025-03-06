package com.deliveryManPlus.order.entity;

import com.deliveryManPlus.common.entity.CreateDateEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @OneToMany(mappedBy = "orderMenu")
    private List<OrderMenuOptionDetail> orderMenuOptionDetail;

    @Builder
    public OrderMenu(String menuName, BigDecimal menuPrice, int quantity) {
        this.name = menuName;
        this.price = menuPrice;
        this.quantity = quantity;
    }

    public void updateOrder(Order order) {
        this.order = order;
    }
}
