package com.deliveryManPlus.service.imp;

import com.deliveryManPlus.constant.error.MenuErrorCode;
import com.deliveryManPlus.constant.error.MenuOptionErrorCode;
import com.deliveryManPlus.dto.menuOption.MenuOptionRequestDto;
import com.deliveryManPlus.entity.Menu;
import com.deliveryManPlus.entity.MenuOption;
import com.deliveryManPlus.entity.MenuOptionDetail;
import com.deliveryManPlus.exception.ApiException;
import com.deliveryManPlus.repository.MenuOptionDetailRepository;
import com.deliveryManPlus.repository.MenuOptionRepository;
import com.deliveryManPlus.repository.MenuRepository;
import com.deliveryManPlus.service.MenuOptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuOptionServiceImpl implements MenuOptionService {
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuOptionDetailRepository menuOptionDetailRepository;

    @Transactional
    @Override
    public void createMenuOptions(Long shopId, Long menuId, List<MenuOptionRequestDto> dtoList) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));

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
    public void deleteById(Long menuId) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new ApiException(MenuErrorCode.NOT_FOUND));

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