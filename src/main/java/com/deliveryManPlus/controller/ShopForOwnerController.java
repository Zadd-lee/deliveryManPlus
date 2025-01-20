package com.deliveryManPlus.controller;

import com.deliveryManPlus.service.imp.UserDetailsImp;
import com.deliveryManPlus.dto.shop.ShopCreateRequestDto;
import com.deliveryManPlus.dto.shop.ShopDetailResponseDto;
import com.deliveryManPlus.dto.shop.ShopStatusRequestDto;
import com.deliveryManPlus.dto.shop.ShopUpdateRequestDto;
import com.deliveryManPlus.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner/shop")
@RequiredArgsConstructor
public class ShopForOwnerController {
    private final ShopService shopService;
    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserDetailsImp userDetailsImp,
                                       @Valid @RequestBody ShopCreateRequestDto dto) {
        shopService.create(userDetailsImp.getBasicAuth().getUser(), dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<ShopDetailResponseDto> updateShop(@PathVariable Long shopId,
                                                            @Valid @RequestBody ShopUpdateRequestDto dto) {

        ShopDetailResponseDto responseDto =shopService.updateShop(shopId, dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopDetailResponseDto> updateStatus(@PathVariable Long shopId,
                                                              @Valid @RequestBody ShopStatusRequestDto status) {
        ShopDetailResponseDto dto = shopService.updateShopStatus(shopId, status.getStatus());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @DeleteMapping("/{shopId}")
    public ResponseEntity<Void> delete(@PathVariable Long shopId) {
        shopService.deleteShop(shopId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
