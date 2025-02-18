package com.deliveryManPlus.dto.shop;

import com.deliveryManPlus.constant.Day;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "상점 수정 요청")
@NoArgsConstructor
@Getter
public class ShopUpdateRequestDto {
    @Schema(description = "상점 이름", example = "금니 정식 원빈 2호점")
    private String name;
    @Schema(description = "상점 주소", example = "서울시 강남구 역삼동 123-45 103동 101호")
    private String address;
    @Schema(description = "오픈 시간", example = "09:00")
    private String openAt;
    @Schema(description = "주문 마감 시간", example = "21:00")
    private String closedAt;
    @Schema(description = "휴무일", example = "MON,TUE"
            ,allowableValues = {"MON","TUE","WED","THU","FRI","SAT","SUN"})
    private List<Day> closedDay;

    @Schema(description = "최소 주문 금액", example = "10000")
    private BigDecimal minimumOrderAmount;



}
