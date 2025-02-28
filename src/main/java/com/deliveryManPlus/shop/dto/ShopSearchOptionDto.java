package com.deliveryManPlus.shop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "상점 검색 옵션")
@NoArgsConstructor
@Getter
public class ShopSearchOptionDto {
    @Schema(description = "카테고리 ID", example = "1")
    @NotNull
    private Long categoryId;

//    private String keyword;
//    private SortOption sortOption;
//    private address
}
