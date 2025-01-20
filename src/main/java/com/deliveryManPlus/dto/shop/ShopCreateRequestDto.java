package com.deliveryManPlus.dto.shop;

import com.deliveryManPlus.constant.Day;
import com.deliveryManPlus.utils.StringUtils;
import com.deliveryManPlus.constant.ShopStatus;
import com.deliveryManPlus.entity.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
public class ShopCreateRequestDto {

    private String registNumber;

    private String name;

    private String address;

    private BigDecimal minimumOrderAmount;

    private String openAt;

    private String closedAt;

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
