package com.deliveryManPlus.menu.dto.menu;

import com.deliveryManPlus.common.exception.constant.errorcode.MenuStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "메뉴 상태 수정 요청")
@Getter
@NoArgsConstructor
public class MenuUpdateStatusRequestDto {
    @Schema(description = "메뉴 상태"
            ,allowableValues = {"USE","SOLD_OUT","NOT_USE"}
            ,example = "USE")
    private MenuStatus status;
}

