package com.deliveryManPlus.menu.entity;

import com.deliveryManPlus.common.entity.CreateDateEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
public class MenuOptionDetail extends CreateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal optionPrice;


    @ManyToOne
    @JoinColumn(name = "menu_option_id")
    private MenuOption menuOption;

    public void updateMenuOption(MenuOption menuOption) {
        this.menuOption = menuOption;
    }

    @Builder
    public MenuOptionDetail(String name, BigDecimal optionPrice, MenuOption menuOption) {
        this.name = name;
        this.optionPrice = optionPrice;
        this.menuOption = menuOption;
    }
}
