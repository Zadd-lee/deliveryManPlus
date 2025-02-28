package com.deliveryManPlus.controller;

import com.deliveryManPlus.dto.menuOption.MenuOptionDto;
import com.deliveryManPlus.service.MenuOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner/{shopId}/menu/{menuId}")
@RequiredArgsConstructor
public class MenuOptionController {
    private final MenuOptionService service;

    @PostMapping
    public ResponseEntity<Void> createMenuOptions(@PathVariable("shopId") Long shopId
            ,@PathVariable("menuId") Long menuId
            , @RequestBody List<MenuOptionDto> dtoList) {
        service.createMenuOptions(shopId,menuId, dtoList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
