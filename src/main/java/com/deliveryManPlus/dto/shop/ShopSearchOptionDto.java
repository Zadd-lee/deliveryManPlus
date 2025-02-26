package com.deliveryManPlus.dto.shop;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ShopSearchOptionDto {
    @NotNull
    private Long categoryId;

//    private String keyword;
//    private SortOption sortOption;
//    private address
}
