package com.deliveryManPlus.image.model.vo;

import com.deliveryManPlus.common.constant.ImageExtension;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.ImageErrorCode;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Getter
@Embeddable
public class ImageName {
    private final String name;
    private final String originalName;

    private final String extension;

    public ImageName(String name) {
        this.name = generateName();
        this.originalName = name;
        this.extension = name.substring(name.lastIndexOf(".")+1);
        if (!ImageExtension.isSupported(this.extension)) {
            throw new ApiException(ImageErrorCode.IMAGE_EXTENSION_NOT_SUPPORTED);
        }
    }

    public ImageName() {
        this.name = null;
        this.originalName = null;
        this.extension = null;
    }

    public ImageName of(String name) {
        return new ImageName(name);
    }

    private String generateName() {
        return UUID.randomUUID().toString();
    }

    public String getFullName() {
        return name + "." + extension;
    }
}
