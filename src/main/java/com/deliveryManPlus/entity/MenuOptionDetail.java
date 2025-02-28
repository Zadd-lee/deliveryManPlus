package com.deliveryManPlus.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
public class MenuOptionDetail extends CreateDateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal optionPrice;


    @ManyToOne
    @JoinColumn(name = "menu_option_id")
    private MenuOption menuOption;

    @Builder
    public MenuOptionDetail(Long id, String name, BigDecimal optionPrice) {
        this.id = id;
        this.name = name;
        this.optionPrice = optionPrice;
    }

    public void updateMenuOption(MenuOption menuOption) {
        this.menuOption = menuOption;
    }
}
