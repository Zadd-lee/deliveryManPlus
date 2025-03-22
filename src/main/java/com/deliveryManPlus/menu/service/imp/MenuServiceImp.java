package com.deliveryManPlus.menu.service.imp;

import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.MenuStatus;
import com.deliveryManPlus.common.exception.constant.errorcode.ShopErrorCode;
import com.deliveryManPlus.image.model.vo.ImageTarget;
import com.deliveryManPlus.image.service.ImageService;
import com.deliveryManPlus.menu.dto.menu.MenuCreateRequestDto;
import com.deliveryManPlus.menu.dto.menu.MenuDetailResponseDto;
import com.deliveryManPlus.menu.dto.menu.MenuUpdateRequestDto;
import com.deliveryManPlus.menu.dto.menu.MenuUpdateStatusRequestDto;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.menu.repository.MenuRepository;
import com.deliveryManPlus.menu.service.MenuService;
import com.deliveryManPlus.shop.constant.ShopStatus;
import com.deliveryManPlus.shop.entity.Shop;
import com.deliveryManPlus.shop.repository.ShopRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.deliveryManPlus.common.utils.EntityValidator.validate;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImp implements MenuService {
    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;

    private final ImageService imageService;

    @Override
    public void create(Long shopId, MenuCreateRequestDto dto, List<MultipartFile> imageList) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        //validation
        if(shop.getStatus().equals(ShopStatus.CLOSED_DOWN)){
            throw new ApiException(ShopErrorCode.NOT_VALUABLE);
        }

        validate(shop);

        Menu menu = dto.toEntity();
        menu.updateShop(shop);

        menuRepository.save(menu);

        //이미지 저장
        ImageTarget imageTarget = new ImageTarget(menu.getId(), this.getClass().getSimpleName());
        imageService.save(imageTarget,imageList);


    }

    @Override
    public void update(Long shopId, Long menuId, @Valid MenuUpdateRequestDto dto, List<MultipartFile> imageList) {
        //검증
        Shop shop = shopRepository.findById(menuId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        Menu menu = menuRepository.findByIdOrElseThrows(menuId);

        validate(shop);
        validate(menu, shop);

        menu.updateByDto(dto);

        //이미지 저장
        ImageTarget imageTarget = new ImageTarget(menu.getId(), this.getClass().getSimpleName());
        imageService.updateImage(imageTarget,imageList);


    }

    @Override
    public void updateStatus(Long shopId, Long menuId, MenuUpdateStatusRequestDto dto) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        Menu menu = menuRepository.findByIdOrElseThrows(menuId);

        validate(shop);
        validate(menu, shop);

        menu.updateStatus(dto.getStatus());
    }

    @Override
    public void delete(Long shopId, Long menuId) {
        //검증
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND));

        Menu menu = menuRepository.findByIdOrElseThrows(menuId);

        validate(shop);
        validate(menu, shop);
        
        menu.updateStatus(MenuStatus.NOT_USE);
    }

    @Override
    public MenuDetailResponseDto findById(Long shopId, Long menuId) {
        Menu menu = menuRepository.findByIdOrElseThrows(menuId);
        //검증
        validate(menu, shopRepository.findById(shopId)
                .orElseThrow(() -> new ApiException(ShopErrorCode.NOT_FOUND)));
        return new MenuDetailResponseDto(menu);
    }

}
