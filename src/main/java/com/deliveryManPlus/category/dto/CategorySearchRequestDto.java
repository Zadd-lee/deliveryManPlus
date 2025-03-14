package com.deliveryManPlus.category.dto;

import com.deliveryManPlus.common.constant.OrderBy;
import com.deliveryManPlus.common.constant.OrderType;
import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CommonErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "카테고리 검색 요청")
@NoArgsConstructor
@Getter
public class CategorySearchRequestDto {

    @Schema(description = "사용 여부", example = "Y", allowableValues = {"Y", "N", "ALL"})
    @Pattern(regexp = "Y|N|ALL")
    String useYn;
    String keyword;
    OrderType orderType;
    OrderBy orderBy;

    public Sort getSort() {
        //검증
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

        if(orderBy != OrderBy.CREATED_DATE && orderBy != OrderBy.UPDATED_DATE){
            throw new ApiException(CommonErrorCode.INVALID_ORDERBY_TYPE);
        }

        return Sort.by(direction, orderBy.getName());

    }

    public String getKeywordForSQL() {
        return keyword == null ? "%" : "%" + keyword + "%";
    }

    public List<Status> getStatusList() {
        List<Status> statusList = new ArrayList<>();
        if (useYn.equals("ALL")) {
            statusList.add(Status.USE);
            statusList.add(Status.DELETED);
        } else {
            statusList.add(Status.of(useYn));
        }
        return statusList;
    }
}
