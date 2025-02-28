package com.deliveryManPlus.dto.menuOption;


import com.deliveryManPlus.entity.MenuOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MenuOptionResponseDto {
    private Long id;
    private String title;
    private String content;
    private Integer selectionLimit;
    private Boolean requirement;


    List<MenuOptionDetailResponseDto> menuOptionDetailResponseDtoList;

    public MenuOptionResponseDto(MenuOption menuOption) {
        this.id = menuOption.getId();
        this.title = menuOption.getTitle();
        this.content = menuOption.getContent();
        this.selectionLimit = menuOption.getSelectionLimit();
        this.requirement = menuOption.getRequirement();
        this.menuOptionDetailResponseDtoList = menuOption.getMenuOptionDetailList().stream()
                .map(MenuOptionDetailResponseDto::new)
                .toList();
    }
}
