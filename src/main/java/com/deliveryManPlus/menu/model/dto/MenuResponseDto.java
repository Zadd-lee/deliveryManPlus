package com.deliveryManPlus.menu.model.dto;

import com.deliveryManPlus.menu.model.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class MenuResponseDto {

    private Long id;
    private String name;
    private String context;
    private BigDecimal price;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.context = menu.getContext();
        this.price = menu.getPrice();
    }
}

