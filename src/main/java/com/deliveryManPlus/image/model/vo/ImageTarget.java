package com.deliveryManPlus.image.model.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ImageTarget {
    private final Long targetId;
    private final String targetName;

    public ImageTarget(Long targetId, String targetName) {
        this.targetId = targetId;
        this.targetName = targetName.contains("Service")
                ? targetName.substring(0,targetName.indexOf("Service")).toUpperCase()
                : targetName.toUpperCase();

    }

    public ImageTarget() {
        this.targetId = null;
        this.targetName = null;
    }

    public static ImageTarget of(Long targetId, String targetName) {
        return new ImageTarget(targetId, targetName);
    }
}
