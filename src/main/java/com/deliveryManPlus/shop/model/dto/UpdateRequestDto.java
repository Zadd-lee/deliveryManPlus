package com.deliveryManPlus.shop.model.dto;

import com.deliveryManPlus.common.constant.Day;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
public class UpdateRequestDto {
    private String name;
    private String address;
    private String openAt;
    private String closedAt;
    private List<Day> closedDay;

    private BigDecimal minimumOrderAmount;



}
