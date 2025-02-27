package com.deliveryManPlus.dto.shop;

import com.deliveryManPlus.constant.Day;
import com.deliveryManPlus.utils.StringUtils;
import com.deliveryManPlus.constant.ShopStatus;
import com.deliveryManPlus.entity.Shop;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Schema(description = "상점 생성 요청 DTO")
@NoArgsConstructor
@Getter
public class ShopCreateRequestDto {

    @Schema(description = "사업자 등록번호", example = "123-45-67890")
    private String registNumber;

    @Schema(description = "카테고리 식별자", example = "1")
    private Long categoryId;

    @Schema(description = "상점 이름", example = "금니 정식 원빈점")
    private String name;

    @Schema(description = "상점 주소", example = "서울시 강남구 역삼동 123-45")
    private String address;

    @Schema(description = "최소 주문 금액", example = "10000")
    private BigDecimal minimumOrderAmount;

    @Schema(description = "오픈 시간", example = "09:00")
    private String openAt;
    @Schema(description = "주문 마감 시간", example = "21:00")
    private String closedAt;
    @Schema(description = "휴무일", example = "MON,TUE"
            ,allowableValues = {"MON","TUE","WED","THU","FRI","SAT","SUN"})
    private List<Day> closedDay;

    public Shop toEntity() {

        String closedDayString = StringUtils.toStringWithComma(this.closedDay);


        return Shop.builder()
                .registNumber(this.registNumber)
                .name(this.name)
                .address(this.address)
                .minimumOrderAmount(this.minimumOrderAmount)
                .openAt(this.openAt)
                .closedAt(this.closedAt)
                .closedDay(closedDayString)
                .status(ShopStatus.CLOSED)
                .build();


    }

}
