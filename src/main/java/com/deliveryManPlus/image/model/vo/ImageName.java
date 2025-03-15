package com.deliveryManPlus.image.model.vo;

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
        this.extension = name.substring(name.lastIndexOf("."));
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

    public boolean isSameExtension(String extension) {
        return this.extension.equals(extension);
    }
}
