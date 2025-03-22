package com.deliveryManPlus.image.model.entity;

import com.deliveryManPlus.common.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.image.model.vo.ImageName;
import com.deliveryManPlus.image.model.vo.ImageTarget;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class Image extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ImageTarget imageTarget;

    @Embedded
    private ImageName imageName;

    private String path;

    public Image(ImageTarget imageTarget, MultipartFile multipartFile) {
        this.imageTarget = imageTarget;
        this.imageName = new ImageName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    }

    public void updatePath(String path) {
        this.path = path;
    }
}
