package com.deliveryManPlus.image.repository;

import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.image.model.vo.ImageTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query("select i.path from Image i where  i.imageTarget.targetId in :shopIdList")
    List<Image> findByShopIdIn(List<Long> shopIdList);

    @Query("select i.path from Image i where i.imageTarget = :imageTarget")
    String findFirstPathByByImageTarget(@Param("imageTarget") ImageTarget imageTarget);
}
