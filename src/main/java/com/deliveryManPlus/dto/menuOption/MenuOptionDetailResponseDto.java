package com.deliveryManPlus.dto.menuOption;

import com.deliveryManPlus.entity.MenuOptionDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class MenuOptionDetailResponseDto {
    private Long id;
    private String name;
    private BigDecimal optionPrice;

    public MenuOptionDetailResponseDto(MenuOptionDetail menuOptionDetail) {
        this.id = menuOptionDetail.getId();
        this.name = menuOptionDetail.getName();
        this.optionPrice = menuOptionDetail.getOptionPrice();
    }
}
