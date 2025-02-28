package com.deliveryManPlus.dto.menuOption;

import com.deliveryManPlus.entity.MenuOptionDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "메뉴 옵션 상세 생성 요청")
@NoArgsConstructor
@Getter
public class MenuOptionDetailReqeustDto {
    @Schema(description = "메뉴 옵션 상세 제목", example = "매우 매움 쓰읍하")
    @NotBlank
    private String name;
    @Schema(description = "메뉴 옵션 상세 가격", example = "300")
    private BigDecimal optionPrice;

    public MenuOptionDetail toEntity() {
        return MenuOptionDetail.builder()
                .name(name)
                .optionPrice(optionPrice)
                .build();
    }
}
