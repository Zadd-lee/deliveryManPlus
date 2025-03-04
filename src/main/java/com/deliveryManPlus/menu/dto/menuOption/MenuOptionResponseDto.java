package com.deliveryManPlus.menu.dto.menuOption;


import com.deliveryManPlus.menu.entity.MenuOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "메뉴 옵션 응답 정보")
@Getter
@NoArgsConstructor
public class MenuOptionResponseDto {
    @Schema(description = "메뉴 옵션 식별자", example = "1")
    private Long id;
    @Schema(description = "메뉴 옵션 제목", example = "매운 맛")
    private String title;
    @Schema(description = "메뉴 옵션 내용", example = "매운 맛 선택")
    private String content;
    @Schema(description = "선택 최소 수량", example = "1")
    private Integer selectionLimit;
    @Schema(description = "필수 여부", example = "true")
    private Boolean requirement;

    @Schema(description = "메뉴 옵션 상세 목록")
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
