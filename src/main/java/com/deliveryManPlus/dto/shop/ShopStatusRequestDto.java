package com.deliveryManPlus.dto.shop;

import com.deliveryManPlus.constant.ShopStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "상점 상태 수정 요청 DTO")
@Getter
@NoArgsConstructor
public class ShopStatusRequestDto {
    @Schema(description = "상점 상태", example = "OPEN", allowableValues = {"OPEN", "CLOSED"})
    @NotNull
    private ShopStatus status;
}
