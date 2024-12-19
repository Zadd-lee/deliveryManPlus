package com.deliveryManPlus.shop.model.dto;

import com.deliveryManPlus.common.constant.Day;
import com.deliveryManPlus.common.utils.StringUtils;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.model.entity.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
public class CreateRequestDto {

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
