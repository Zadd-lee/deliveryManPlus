package com.deliveryManPlus.shop.service.imp;

import com.deliveryManPlus.common.exception.constant.SessionErrorCode;
import com.deliveryManPlus.common.exception.constant.ShopErrorCode;
import com.deliveryManPlus.common.exception.exception.ApiException;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.model.dto.CreateRequestDto;
import com.deliveryManPlus.shop.model.dto.ShopDetailResponseDto;
import com.deliveryManPlus.shop.model.dto.ShopResponseDto;
import com.deliveryManPlus.shop.model.dto.UpdateRequestDto;
import com.deliveryManPlus.shop.model.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import com.deliveryManPlus.shop.service.ShopService;
import com.deliveryManPlus.user.model.entity.User;
import com.deliveryManPlus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Override
    public void create(Long userId, CreateRequestDto dto) {
        //검증
        User user = userRepository.findById(userId).get();

        //todo bug 발견 / 존재 해도 폐업 에러 출력
        if (shopRepository.existsShopByRegistNumber(dto.getRegistNumber())) {
            throw new ApiException(ShopErrorCode.NOT_VALUABLE);
        }
        Shop shop = dto.toEntity();
        shop.updateOwner(user);

        shopRepository.save(shop);
    }

    @Override
    public List<ShopResponseDto> findAll() {
        List<Shop> shopList = shopRepository.findAllNotClosedDown();

        return shopList.stream()
                .map(ShopResponseDto::new)
                .toList();


    }

    @Override
    public ShopDetailResponseDto findById(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        if (shop.getStatus() == ShopStatus.CLOSED_DOWN) {
            throw new ApiException(ShopErrorCode.NOT_VALUABLE);
        }
        return new ShopDetailResponseDto(shop, shop.getMenuList());

    }

    @Override
    public ShopDetailResponseDto updateShop(Long shopId, Long userId, UpdateRequestDto dto) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));


        if (shop.getOwner().getId()!=userId) {
            throw new ApiException(SessionErrorCode.NOT_ALLOWED);
        }
        shop.updateByDto(dto);
        return new ShopDetailResponseDto(shop, shop.getMenuList());
    }

    @Override
    public ShopDetailResponseDto updateShopStatus(Long shopId, Long userId, ShopStatus status) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        if (shop.getOwner().getId() !=userId) {
            throw new ApiException(SessionErrorCode.NOT_ALLOWED);
        }
        if (shop.getStatus() == ShopStatus.CLOSED_DOWN) {
            throw new ApiException(ShopErrorCode.NOT_FOUND);
        }
        shop.updateStatus(status);
        return new ShopDetailResponseDto(shop, shop.getMenuList());
    }

    @Override
    public void deleteShop(Long shopId, Long userId) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        if (shop.getOwner().getId() != userId) {
            throw new ApiException(SessionErrorCode.NOT_ALLOWED);
        }
        if (shop.getStatus() == ShopStatus.CLOSED_DOWN) {
            throw new ApiException(ShopErrorCode.NOT_FOUND);
        }
        shop.updateStatus(ShopStatus.CLOSED_DOWN);
    }
}
