package com.deliveryManPlus.dto.shop;

import com.deliveryManPlus.constant.Day;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
public class ShopUpdateRequestDto {
    private String name;
    private String address;
    private String openAt;
    private String closedAt;
    private List<Day> closedDay;

    private BigDecimal minimumOrderAmount;



}
