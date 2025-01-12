package com.deliveryManPlus.dto.menu;

import com.deliveryManPlus.constant.error.MenuStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuUpdateStatusRequestDto {

    private MenuStatus status;
}

