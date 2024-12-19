package com.deliveryManPlus.shop.service.imp;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.common.exception.constant.SessionErrorCode;
import com.deliveryManPlus.common.exception.constant.ShopErrorCode;
import com.deliveryManPlus.common.exception.exception.ApiException;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.model.dto.CreateRequestDto;
import com.deliveryManPlus.shop.model.dto.ShopResponseDto;
import com.deliveryManPlus.shop.model.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import com.deliveryManPlus.shop.service.ShopService;
import com.deliveryManPlus.user.model.entity.User;
import com.deliveryManPlus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Override
    public void create(Authentication auth, CreateRequestDto dto) {
        //검증
        User user = userRepository.findById(auth.getId())
                .orElseThrow(() -> new ApiException(SessionErrorCode.NOT_ALLOWED));

        Shop shop = dto.toEntity();
        shop.updateOwner(user);

        shopRepository.save(shop);
    }

    @Override
    public List<ShopResponseDto> findAll() {
        List<Shop> shopList = shopRepository.findAllNotClosedDown();

        return shopList.stream()
                .map(x -> new ShopResponseDto(x))
                .toList();


    }

    @Override
    public ShopResponseDto findById(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUNT));

        if(shop.getStatus() == ShopStatus.CLOSED_DOWN){
            throw new ApiException(ShopErrorCode.NOT_VALUABLE);
        }
        return new ShopResponseDto(shop);

    }
}
