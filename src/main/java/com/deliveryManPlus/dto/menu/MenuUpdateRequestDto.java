package com.deliveryManPlus.dto.menu;

import com.deliveryManPlus.constant.error.MenuStatus;
import com.deliveryManPlus.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "메뉴 수정 요청")
@Getter
@NoArgsConstructor
public class MenuUpdateRequestDto {

    @Schema(description = "메뉴 이름", example = "금니 정식 스페셜")
    private String name;
    @Schema(description = "메뉴 설명", example = "금니 빼고 다 씹어먹을만한 맛! 스페셜 버전")
    private String context;
    @Schema(description = "메뉴 가격", example = "15000")
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

