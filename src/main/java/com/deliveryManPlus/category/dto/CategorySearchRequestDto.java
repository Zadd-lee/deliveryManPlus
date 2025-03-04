package com.deliveryManPlus.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "카테고리 검색 요청")
@NoArgsConstructor
@Getter
public class CategorySearchRequestDto {

    @Schema(description = "사용 여부", example = "Y", allowableValues = {"Y", "N","ALL"})
    String useYn;
//    String name;
}
