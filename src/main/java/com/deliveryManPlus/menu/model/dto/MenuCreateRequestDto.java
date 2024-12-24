package com.deliveryManPlus.menu.model.dto;

import com.deliveryManPlus.menu.constant.MenuStatus;
import com.deliveryManPlus.menu.model.entity.Menu;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class MenuCreateRequestDto {

    @NotNull
    private String name;
    private String context;
    @NotNull
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

