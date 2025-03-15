package com.deliveryManPlus.image.service;

import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.ImageErrorCode;
import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.image.model.vo.ImageTarget;
import com.deliveryManPlus.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final S3UploadService s3UploadService;


    @Transactional
    public void save(ImageTarget imageTarget, List<MultipartFile> multipartFileList)  {

        // 이미지 개수 제한
        if (multipartFileList.size() > 10) {
            throw new ApiException(ImageErrorCode.IMAGE_COUNT_EXCEED);
        }else if(multipartFileList.isEmpty()){
            return;
        }

        List<Image> list = multipartFileList.stream()
                .map(multipartFile -> {
                    Image image = new Image(imageTarget, multipartFile);
                    try {
                        String path = s3UploadService.saveFile(multipartFile, image.getImageName().getName());
                        image.updatePath(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ApiException(ImageErrorCode.IMAGE_SAVE_ERROR);
                    }
                    return image;
                })
                .toList();

        imageRepository.saveAll(list);
    }

    public List<String> findImageByShopList(List<Long> shopIdList) {
        List<String> imagePathList = shopIdList.stream()
                .map(id -> imageRepository.findFirstPathByByImageTarget(new ImageTarget(id, "shop")))
                .toList();
        return imagePathList;
    }
}
