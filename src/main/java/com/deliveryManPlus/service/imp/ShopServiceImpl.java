package com.deliveryManPlus.service.imp;

import com.deliveryManPlus.constant.ShopStatus;
import com.deliveryManPlus.constant.Status;
import com.deliveryManPlus.constant.error.CategoryErrorCode;
import com.deliveryManPlus.constant.error.ShopErrorCode;
import com.deliveryManPlus.dto.shop.*;
import com.deliveryManPlus.entity.Category;
import com.deliveryManPlus.entity.Shop;
import com.deliveryManPlus.entity.User;
import com.deliveryManPlus.exception.ApiException;
import com.deliveryManPlus.repository.CategoryRepository;
import com.deliveryManPlus.repository.ShopRepository;
import com.deliveryManPlus.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.deliveryManPlus.utils.EntityValidator.validate;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void create(User user, ShopCreateRequestDto dto) {
        //검증
        shopRepository.findByRegistNumber(dto.getRegistNumber())
                .ifPresent(shop -> {
                    if (shop.getStatus() != ShopStatus.CLOSED_DOWN) {
                        throw new ApiException(ShopErrorCode.NOT_VALUABLE);
                    }
                    throw new ApiException(ShopErrorCode.DUPLICATED_SHOP);
                });

        Category category = categoryRepository.findByIdOrElseThrows(dto.getCategoryId());
        if (category.getStatus() == Status.DELETED) {
            throw new ApiException(CategoryErrorCode.NOT_VALUABLE);
        }


        Shop shop = dto.toEntity();

        shop.updateOwner(user);
        shop.updateCategory(category);


        shopRepository.save(shop);
    }

    @Override
    public List<ShopResponseDto> findAll(ShopSearchOptionDto dto) {

        return shopRepository.findAll(dto.getCategoryId())
                .stream()
                .map(ShopResponseDto::new)
                .toList();


    }

    @Override
    public ShopDetailResponseDto findById(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        validateShopStatus(shop);

        return new ShopDetailResponseDto(shop, shop.getMenuList());

    }

    @Override
    public ShopDetailResponseDto updateShop(Long shopId, ShopUpdateRequestDto dto) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        validate(shop);

        shop.updateByDto(dto);
        return new ShopDetailResponseDto(shop, shop.getMenuList());
    }


    @Override
    public ShopDetailResponseDto updateShopStatus(Long shopId, ShopStatus status) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        validate(shop);
        validateShopStatus(shop);

        shop.updateStatus(status);
        return new ShopDetailResponseDto(shop, shop.getMenuList());
    }

    @Override
    public void deleteShop(Long shopId) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        validate(shop);
        validateShopStatus(shop);

        shop.updateStatus(ShopStatus.CLOSED_DOWN);
    }

    //shop의 status가 폐업이 아닌지 확인
    private static void validateShopStatus(Shop shop) {
        if (shop.getStatus() == ShopStatus.CLOSED_DOWN) {
            throw new ApiException(ShopErrorCode.NOT_VALUABLE);
        }
    }
}
