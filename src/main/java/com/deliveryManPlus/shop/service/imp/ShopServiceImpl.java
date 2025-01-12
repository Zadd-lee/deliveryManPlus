package com.deliveryManPlus.shop.service.imp;

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
    public void create(User user, CreateRequestDto dto) {
        //검증
        if (shopRepository.findByRegistNumber(dto.getRegistNumber()).isPresent()) {
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

        validateShopStatus(shop);

        return new ShopDetailResponseDto(shop, shop.getMenuList());

    }

    @Override
    public ShopDetailResponseDto updateShop(Long shopId, UpdateRequestDto dto) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
        shop.updateByDto(dto);
        return new ShopDetailResponseDto(shop, shop.getMenuList());
    }


    @Override
    public ShopDetailResponseDto updateShopStatus(Long shopId, ShopStatus status) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        validateShopStatus(shop);

        shop.updateStatus(status);
        return new ShopDetailResponseDto(shop, shop.getMenuList());
    }

    @Override
    public void deleteShop(Long shopId) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));
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
