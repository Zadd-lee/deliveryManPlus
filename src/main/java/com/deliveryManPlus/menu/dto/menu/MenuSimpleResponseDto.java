package com.deliveryManPlus.menu.dto.menu;

import com.deliveryManPlus.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class MenuSimpleResponseDto {

    private Long id;
    private String name;
    private String context;
    private BigDecimal price;

    public MenuSimpleResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.context = menu.getContext();
        this.price = menu.getPrice();
    }
}

