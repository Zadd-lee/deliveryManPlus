package com.deliveryManPlus.image.repository;

import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.image.model.vo.ImageTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query("select i.path from Image i where  i.imageTarget.targetId in :shopIdList")
    List<Image> findByShopIdIn(List<Long> shopIdList);

    Image findFirstByImageTarget(ImageTarget imageTarget);

    List<Image> findByImageTarget(ImageTarget shop);

    void deleteByImageTarget(ImageTarget imageTarget);
}
