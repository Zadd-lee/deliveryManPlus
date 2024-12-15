package com.deliveryManPlus.shop.model.entity;

import com.deliveryManPlus.common.model.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@Getter
@Builder
public class Shop extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String registNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private BigDecimal minimumOrderAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShopStatus status;

    @Column(nullable = false)
    private String openAt;

    @Column(nullable = false)
    private String closedAt;

    @Column(nullable = false)
    private String closedDay;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Shop() {
    }

    public void updateOwner(User user) {
        this.owner= user;
    }
}
