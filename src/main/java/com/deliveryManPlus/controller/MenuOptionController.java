package com.deliveryManPlus.controller;

import com.deliveryManPlus.dto.menuOption.MenuOptionRequestDto;
import com.deliveryManPlus.service.MenuOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner/{shopId}/menu/{menuId}/menuOption")
@RequiredArgsConstructor
public class MenuOptionController {
    private final MenuOptionService service;

    @PostMapping
    public ResponseEntity<Void> createMenuOptions(@PathVariable(name = "shopId") Long shopId
            , @PathVariable("menuId") Long menuId
            , @RequestBody List<MenuOptionRequestDto> dtoList) {
        service.createMenuOptions(shopId, menuId, dtoList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllByMenuId(@PathVariable(name = "shopId") Long shopId
            , @PathVariable("menuId") Long menuId) {
        service.deleteById(menuId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
