package com.deliveryManPlus.shop.service.imp;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.category.entity.Category;
import com.deliveryManPlus.category.repository.CategoryRepository;
import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.CategoryErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.ShopErrorCode;
import com.deliveryManPlus.image.model.entity.Image;
import com.deliveryManPlus.image.model.vo.ImageTarget;
import com.deliveryManPlus.image.service.ImageService;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.dto.*;
import com.deliveryManPlus.shop.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import com.deliveryManPlus.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.deliveryManPlus.common.utils.EntityValidator.validate;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;

    private final ImageService imageService;

    @Override
    public void create(User user, ShopCreateRequestDto dto, List<MultipartFile> imageList) {
        //검증
        shopRepository.findByRegistNumber(dto.getRegistNumber())
                .ifPresent(shop -> {
                    if (shop.getStatus() != ShopStatus.CLOSED_DOWN) {
                        throw new ApiException(ShopErrorCode.DUPLICATED_SHOP);
                    }
                    throw new ApiException(ShopErrorCode.NOT_VALUABLE);
                });

        Category category = categoryRepository.findByIdOrElseThrows(dto.getCategoryId());
        if (category.getStatus() == Status.DELETED) {
            throw new ApiException(CategoryErrorCode.NOT_VALUABLE);
        }

        Shop shop = dto.toEntity();

        shop.updateOwner(user);
        shop.updateCategory(category);


        shopRepository.save(shop);

        //이미지 업로드
        ImageTarget imageTarget = new ImageTarget(shop.getId(), this.getClass().getSimpleName());
        imageService.save(imageTarget, imageList);
    }

    @Override
    public Page<ShopResponseDto> findAll(ShopSearchOptionDto dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Shop> shopPage = shopRepository.findAllByDto(dto, pageable);
        List<Shop> content = shopPage.getContent();
        List<ImageTarget> imageTargetList = content
                .stream()
                .map(Shop::getId)
                .map(id -> new ImageTarget(id, this.getClass().getSimpleName()))
                .toList();
        List<Image> imageList = imageService.findImageByTargetList(imageTargetList);

        return shopPage.map(shop -> new ShopResponseDto(shop, imageList));


    }


    @Override
    public ShopDetailResponseDto findById(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        validateShopStatus(shop);

        List<Image> imageList = imageService.findImageByTarget(new ImageTarget(shopId, this.getClass().getSimpleName()));

        List<ImageTarget> menuTargetList = shop.getMenuList().stream()
                .map(menu -> new ImageTarget(menu.getId(), "menu"))
                .toList();
        List<Image> menuImageList = imageService.findImageByTargetList(menuTargetList);

        return new ShopDetailResponseDto(shop, shop.getMenuList(),imageList,menuImageList);

    }

    @Override
    public void updateShop(Long shopId, ShopUpdateRequestDto dto, List<MultipartFile> imageList) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        Category category = categoryRepository.findByIdOrElseThrows(dto.getCategoryId());
        if (category.getStatus() == Status.DELETED) {
            throw new ApiException(CategoryErrorCode.NOT_VALUABLE);
        }

        validate(shop);

        shop.updateByDto(dto);
        shop.updateCategory(category);

        ImageTarget imageTarget = new ImageTarget(shop.getId(), this.getClass().getSimpleName());

        //이미지 수정
        imageService.updateImage(imageTarget,imageList);
    }


    @Override
    public void updateShopStatus(Long shopId, ShopStatus status) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        validate(shop);
        validateShopStatus(shop);

        shop.updateStatus(status);
    }

    @Override
    public void deleteShop(Long shopId) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        validate(shop);
        validateShopStatus(shop);

        shop.updateStatus(ShopStatus.CLOSED_DOWN);

        //이미지 삭제
        ImageTarget imageTarget = new ImageTarget(shopId, "shop");
        imageService.deleteAll(imageTarget);
    }

    //shop의 status가 폐업이 아닌지 확인
    private static void validateShopStatus(Shop shop) {
        if (shop.getStatus() == ShopStatus.CLOSED_DOWN) {
            throw new ApiException(ShopErrorCode.NOT_VALUABLE);
        }
    }
}
