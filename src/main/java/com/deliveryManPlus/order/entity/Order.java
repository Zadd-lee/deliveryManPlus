package com.deliveryManPlus.order.entity;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.cart.entity.CartMenu;
import com.deliveryManPlus.common.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.common.exception.constant.errorcode.OrderStatus;
import com.deliveryManPlus.common.utils.Calculator;
import com.deliveryManPlus.review.entity.Review;
import com.deliveryManPlus.shop.entity.Shop;
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

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private final List<OrderMenu> orderMenu = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "order")
    private List<Review> reviewList;

    @Builder
    public Order(User customer, Shop shop) {
        this.status = OrderStatus.SUBMIT;
        this.customer = customer;
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

    public Order(Cart cart) {
        this.customer = cart.getCustomer();
        List<CartMenu> cartMenuList = cart.getCartMenuList();
        this.shop = cartMenuList.stream()
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("주문할 메뉴가 없습니다."))
                .getMenu().getShop();

        this.status = OrderStatus.SUBMIT;

        this.totalPrice = cart.getCartMenuList()
                .stream()
                .map(cartMenu -> {
                    BigDecimal menuPrice = Calculator.calculateMenuPrice(cartMenu);
                    return menuPrice.multiply(BigDecimal.valueOf(cartMenu.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
