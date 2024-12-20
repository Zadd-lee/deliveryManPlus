package com.deliveryManPlus.menu.model.dto;

import com.deliveryManPlus.menu.constant.MenuStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuUpdateStatusRequestDto {

    private MenuStatus status;
}

