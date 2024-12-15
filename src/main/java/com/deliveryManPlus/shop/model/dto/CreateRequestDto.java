package com.deliveryManPlus.shop.model.dto;

import com.deliveryManPlus.common.constant.Day;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.model.entity.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

        //todo util 화 할 지 고민
        String closedDayString = this.closedDay.stream()
                .map(x -> x.toString())
                .collect(Collectors.joining(", "));


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
