package com.deliveryManPlus.utils;

import com.deliveryManPlus.constant.Day;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtils extends org.springframework.util.StringUtils {
    public static String toStringWithComma(List<Day> list){
        return list.stream()
                .map(x -> x.toString())
                .collect(Collectors.joining(", "));
    }
}
