package com.deliveryManPlus.review.entity;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.common.entity.CreateDateEntity;
import com.deliveryManPlus.order.entity.Order;
import com.deliveryManPlus.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Review  extends CreateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Integer score;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public Review(String content, Integer score, User customer, Shop shop, Order order) {
        this.content = content;
        this.score = score;
        this.status = Status.USE;
        this.customer = customer;
        this.shop = shop;
        this.order = order;
    }
}
