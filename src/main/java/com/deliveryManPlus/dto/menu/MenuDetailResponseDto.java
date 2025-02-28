package com.deliveryManPlus.dto.menu;

import com.deliveryManPlus.dto.menuOption.MenuOptionResponseDto;
import com.deliveryManPlus.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class MenuDetailResponseDto {

    private Long id;
    private String name;
    private String context;
    private BigDecimal price;

    List<MenuOptionResponseDto> menuOptionResponseDtoList;

    public MenuDetailResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.context = menu.getContext();
        this.price = menu.getPrice();
        this.menuOptionResponseDtoList = menu.getMenuOptionList().stream()
                .map(MenuOptionResponseDto::new)
                .toList();
    }
}

