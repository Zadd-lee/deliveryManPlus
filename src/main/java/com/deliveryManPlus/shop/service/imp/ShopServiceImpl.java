package com.deliveryManPlus.shop.service.imp;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.common.exception.constant.SessionErrorCode;
import com.deliveryManPlus.common.exception.exception.ApiException;
import com.deliveryManPlus.shop.model.dto.CreateRequestDto;
import com.deliveryManPlus.shop.model.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import com.deliveryManPlus.shop.service.ShopService;
import com.deliveryManPlus.user.model.entity.User;
import com.deliveryManPlus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
