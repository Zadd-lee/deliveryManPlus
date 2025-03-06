package com.deliveryManPlus.order.entity;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.common.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.common.exception.constant.errorcode.OrderStatus;
import com.deliveryManPlus.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "orders")
public class Order extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal totalPrice;

    private String rejectReason;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenu;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public Order(BigDecimal totalPrice, User customer, List<OrderMenu> orderMenu, Shop shop) {
        this.status = OrderStatus.SUBMIT;
        this.totalPrice = totalPrice;
        this.rejectReason = rejectReason;
        this.customer = customer;
        this.orderMenu = orderMenu;
        this.shop = shop;
    }

    public void updateOrderMenu(List<OrderMenu> orderMenuList) {
        this.orderMenu.addAll(orderMenuList);
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void reject(String rejectReason) {
        this.status = OrderStatus.REJECTED;
        this.rejectReason = rejectReason;
    }
}
