package com.deliveryManPlus.dto.menuOption;

import com.deliveryManPlus.entity.MenuOption;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class MenuOptionDto {
    @NotBlank
    private String title;
    private String content;
    private Integer selectionLimit;
    @NotNull
    private Boolean requirement;

    @NotNull @Valid
    List<MenuOptionDetailDto> menuOptionDetailDtoList;

    public MenuOption toEntity() {

        return MenuOption.builder()
                .title(title)
                .content(content)
                .selectionLimit(selectionLimit)
                .requirement(requirement)
                .menuOptionDetailList(
                        menuOptionDetailDtoList.stream()
                                .map(MenuOptionDetailDto::toEntity)
                                .toList()
                )
                .build();
    }
}
