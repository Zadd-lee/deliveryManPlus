package com.deliveryManPlus.menu.dto.menu;

import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.menu.dto.menuOption.MenuOptionResponseDto;
import com.deliveryManPlus.menu.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Schema(description = "메뉴 상세 응답 정보")
@Getter
@NoArgsConstructor
public class MenuDetailResponseDto {

    @Schema(description = "메뉴 식별자", example = "1")
    private Long id;
    @Schema(description = "메뉴 이름", example = "치킨")
    private String name;
    @Schema(description = "메뉴 설명", example = "금니 정식")
    private String context;
    @Schema(description = "메뉴 가격", example = "15000")
    private BigDecimal price;

    @Schema(description = "메뉴 옵션 목록")
    List<MenuOptionResponseDto> menuOptionResponseDtoList;

    List<String> imagePathList;

    public MenuDetailResponseDto(Menu menu, List<Image> imageList) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.context = menu.getContext();
        this.price = menu.getPrice();
        this.menuOptionResponseDtoList = menu.getMenuOptionList().stream()
                .map(MenuOptionResponseDto::new)
                .toList();
        this.imagePathList = imageList.stream()
                .map(Image::getPath)
                .toList();
    }
}

