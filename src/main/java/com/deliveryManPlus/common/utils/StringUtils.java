package com.deliveryManPlus.common.utils;

import com.deliveryManPlus.common.constant.Day;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StringUtils extends org.springframework.util.StringUtils {
    public static String toStringWithComma(List<Day> list){
        return list.stream()
                .map(x -> x.toString())
                .collect(Collectors.joining(", "));
    }

    public static String generateRandomCode(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }
}
