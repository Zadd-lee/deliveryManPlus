package com.deliveryManPlus.menu.dto.menuOption;

import com.deliveryManPlus.menu.entity.MenuOption;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "메뉴 옵션 생성 요청")
@NoArgsConstructor
@Getter
public class MenuOptionRequestDto {
    @Schema(description = "메뉴 옵션 제목", example = "맵기 선택")
    @NotBlank
    private String title;
    @Schema(description = "메뉴 옵션 설명", example = "맵기를 선택해주세요.")
    private String content;
    @Schema(description = "선택 최소 수량", example = "1")
    private Integer selectionLimit;
    @Schema(description = "필수 여부", example = "true")
    @NotNull
    private Boolean requirement;

    @Schema(description = "메뉴 옵션 상세 리스트")
    @NotNull @Valid
    List<MenuOptionDetailReqeustDto> menuOptionDetailReqeustDtoList;

    public MenuOption toEntity() {

        return MenuOption.builder()
                .title(title)
                .content(content)
                .selectionLimit(selectionLimit)
                .requirement(requirement)
                .menuOptionDetailList(
                        menuOptionDetailReqeustDtoList.stream()
                                .map(MenuOptionDetailReqeustDto::toEntity)
                                .toList()
                )
                .build();
    }
}
