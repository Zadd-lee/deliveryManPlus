package com.deliveryManPlus.dto.menuOption;

import com.deliveryManPlus.entity.MenuOptionDetail;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class MenuOptionDetailReqeustDto {
    @NotBlank
    private String name;
    private BigDecimal optionPrice;

    public MenuOptionDetail toEntity() {
        return MenuOptionDetail.builder()
                .name(name)
                .optionPrice(optionPrice)
                .build();
    }
}
