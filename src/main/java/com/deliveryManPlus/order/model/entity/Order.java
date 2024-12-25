package com.deliveryManPlus.order.model.entity;

import com.deliveryManPlus.common.model.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.order.constant.OrderStatus;
import com.deliveryManPlus.shop.model.entity.Shop;
import com.deliveryManPlus.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
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


    public Order(MenuHistory menuHistory, User customer) {
        this.status = OrderStatus.SUBMIT;
        this.totalPrice = menuHistory.getPrice();
        this.customer = customer;
        this.orderMenu = new ArrayList<>();
    }
    public void updateOrderMenu(List<OrderMenu> orderMenuList) {
        this.orderMenu.addAll(orderMenuList);
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }
}
