package com.deliveryManPlus.menu.entity;

import com.deliveryManPlus.common.entity.CreateDateEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class MenuHistory extends CreateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String context;
    private BigDecimal price;

    @Builder
    public MenuHistory(Menu menu) {
        this.name = menu.getName();
        this.context = menu.getContext();
        this.price = menu.getPrice();
    }
}
