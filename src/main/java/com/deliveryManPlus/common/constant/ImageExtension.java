package com.deliveryManPlus.common.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ImageExtension {
    JPG ("jpg"),
    JPEG("jpeg"),
    PNG ("png"),;

    private final String value;


    ImageExtension(String value) {
        this.value = value;
    }


    public static boolean isSupported(String extension) {
        return Arrays.stream(values())
                .anyMatch(e -> e.value.equalsIgnoreCase(extension));
    }

}
