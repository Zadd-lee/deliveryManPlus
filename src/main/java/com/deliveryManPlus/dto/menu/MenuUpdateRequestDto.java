package com.deliveryManPlus.dto.menu;

import com.deliveryManPlus.constant.error.MenuStatus;
import com.deliveryManPlus.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class MenuUpdateRequestDto {

    private String name;
    private String context;
    private BigDecimal price;


    public Menu toEntity(){

        return Menu.builder()
                .status(MenuStatus.USE)
                .name(name)
                .context(context)
                .price(price)
                .build();
    }
}

