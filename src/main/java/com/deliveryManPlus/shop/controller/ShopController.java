package com.deliveryManPlus.shop.controller;

import com.deliveryManPlus.shop.model.dto.ShopDetailResponseDto;
import com.deliveryManPlus.shop.model.dto.ShopResponseDto;
import com.deliveryManPlus.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;
    @GetMapping
    public ResponseEntity<List<ShopResponseDto>> findAll(){
        List<ShopResponseDto> responseDtos = shopService.findAll();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDetailResponseDto> findById(@PathVariable Long shopId){
        ShopDetailResponseDto dto = shopService.findById(shopId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
