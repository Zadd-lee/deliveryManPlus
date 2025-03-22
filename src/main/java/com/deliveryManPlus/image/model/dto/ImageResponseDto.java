package com.deliveryManPlus.image.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ImageResponseDto {
    private Long ImageId;
    private String name;
    private String path;
}
