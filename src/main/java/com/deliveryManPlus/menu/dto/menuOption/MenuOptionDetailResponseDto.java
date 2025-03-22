package com.deliveryManPlus.menu.dto.menuOption;

import com.deliveryManPlus.menu.entity.MenuOptionDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "메뉴 옵션 상세 응답 정보")
@Getter
@NoArgsConstructor
public class MenuOptionDetailResponseDto {
    @Schema(description = "메뉴 옵션 상세 식별자", example = "1")
    private Long id;
    @Schema(description = "메뉴 옵션 상세 이름", example = "너무 매워 쓰읍하")
    private String name;
    @Schema(description = "메뉴 옵션 상세 가격", example = "300")
    private BigDecimal optionPrice;

    public MenuOptionDetailResponseDto(MenuOptionDetail menuOptionDetail) {
        this.id = menuOptionDetail.getId();
        this.name = menuOptionDetail.getName();
        this.optionPrice = menuOptionDetail.getOptionPrice();
    }
}
