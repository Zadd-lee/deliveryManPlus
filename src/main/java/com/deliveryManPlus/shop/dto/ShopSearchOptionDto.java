package com.deliveryManPlus.shop.dto;

import com.deliveryManPlus.common.constant.OrderBy;
import com.deliveryManPlus.common.constant.OrderType;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CommonErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Schema(description = "상점 검색 옵션")
@NoArgsConstructor
@Getter
public class ShopSearchOptionDto {
    @Schema(description = "카테고리 ID", example = "1")
    @NotNull
    private Long categoryId;

    private String keyword;
    private OrderBy orderBy;
    private OrderType orderType;

    public String getKeywordForSQL() {
        return keyword == null ? "%" : "%" + keyword + "%";
    }
    public Sort getSort(){
        if (orderType == null || orderBy == null) {
            if (orderType == null && orderBy == null) {
                return Sort.unsorted();
            }
            throw new ApiException(CommonErrorCode.PARAMETER_ERROR);
        }

        Sort.Direction direction = switch (orderType) {
            case ASC -> Sort.Direction.ASC;
            case DESC -> Sort.Direction.DESC;
        };


        return Sort.by(direction, orderBy.getName());

    }
}
