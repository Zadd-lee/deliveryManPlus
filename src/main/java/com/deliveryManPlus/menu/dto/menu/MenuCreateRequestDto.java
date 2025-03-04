package com.deliveryManPlus.menu.dto.menu;

import com.deliveryManPlus.common.exception.constant.errorcode.MenuStatus;
import com.deliveryManPlus.menu.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "메뉴 생성 요청")
@Getter
@NoArgsConstructor
public class MenuCreateRequestDto {

    @Schema(description = "메뉴 이름", example = "금니 정식")
    @NotNull
    private String name;
    @Schema(description = "메뉴 설명", example = "금니 빼고 다 씹어먹을만한 맛!")
    private String context;
    @Schema(description = "메뉴 가격", example = "10000")
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

