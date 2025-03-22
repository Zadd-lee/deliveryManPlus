package com.deliveryManPlus.menu.service.imp;

import com.deliveryManPlus.common.exception.constant.errorcode.MenuErrorCode;
import com.deliveryManPlus.common.exception.constant.errorcode.MenuOptionErrorCode;
import com.deliveryManPlus.menu.dto.menuOption.MenuOptionRequestDto;
import com.deliveryManPlus.menu.entity.Menu;
import com.deliveryManPlus.menu.entity.MenuOption;
import com.deliveryManPlus.menu.entity.MenuOptionDetail;
import com.deliveryManPlus.shop.entity.Shop;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.menu.repository.MenuOptionDetailRepository;
import com.deliveryManPlus.menu.repository.MenuOptionRepository;
import com.deliveryManPlus.menu.repository.MenuRepository;
import com.deliveryManPlus.shop.repository.ShopRepository;
import com.deliveryManPlus.menu.service.MenuOptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.deliveryManPlus.common.utils.EntityValidator.validate;

@Service
@RequiredArgsConstructor
public class MenuOptionServiceImpl implements MenuOptionService {
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuOptionDetailRepository menuOptionDetailRepository;
    private final ShopRepository shopRepository;

    @Transactional
    @Override
    public void createMenuOptions(Long shopId, Long menuId, List<MenuOptionRequestDto> dtoList) {

        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));
        Menu menu = menuRepository.findByIdOrElseThrows(menuId);
        //검증
        validate(shop);
        validate(menu, shop);

        // DTO 검증
        dtoList.forEach(dto1 -> {
            if (dto1.getRequirement() && dto1.getSelectionLimit() == null) {
                throw new ApiException(MenuOptionErrorCode.REQUIRED_SELECTION_LIMIT);
            }
        });


        //menuOption 존재시 초기화 후 저장
        if (menuOptionRepository.existsByMenuId(menuId)) {
            deleteAllMenuOptionOfMenu(menu);
        }


        //menuOption 저장
        // MenuOption 저장
        List<MenuOption> menuOptionList = dtoList.stream()
                .map(dto ->
                        {
                            MenuOption menuOption = dto.toEntity();
                            menuOption.updateMenu(menu);

                            //menuOptionDetail 저장
                            List<MenuOptionDetail> menuOptionDetailList = menuOption.getMenuOptionDetailList()
                                    .stream()
                                    .peek(menuOptionDetail -> menuOptionDetail.updateMenuOption(menuOption)).toList();
                            menuOptionDetailRepository.saveAll(menuOptionDetailList);

                            menuOption.updateMenuOptionDetailList(menuOptionDetailList);

                            return menuOption;
                        }
                ).toList();

        // menuOption 저장
        menuOptionRepository.saveAll(menuOptionList);

    }

    @Override
    public void deleteById(Long shopId, Long menuId) {

        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));
        Menu menu = menuRepository.findByIdOrElseThrows(menuId);
        
        //검증
        validate(shop);
        validate(menu, shop);

        deleteAllMenuOptionOfMenu(menu);
    }

    private void deleteAllMenuOptionOfMenu(Menu menu) {
        List<MenuOption> menuOptionList = menu.getMenuOptionList();
        //menuOption 초기화
        List<Long> menuOptionIdList = menuOptionList.stream()
                .map(MenuOption::getId)
                .toList();
        menuOptionRepository.deleteAllByIds(menuOptionIdList);
        //menuOptionDetail 초기화
        menuOptionDetailRepository.deleteAllByMenuOptionId(menuOptionIdList);


        //menu list 초기화
        menu.getMenuOptionList().clear();

    }

}