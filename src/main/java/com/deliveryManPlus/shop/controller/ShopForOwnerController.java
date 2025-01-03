package com.deliveryManPlus.shop.controller;

import com.deliveryManPlus.auth.constant.SessionConst;
import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.common.utils.SessionValidator;
import com.deliveryManPlus.shop.model.dto.CreateRequestDto;
import com.deliveryManPlus.shop.model.dto.ShopDetailResponseDto;
import com.deliveryManPlus.shop.model.dto.ShopStatusRequestDto;
import com.deliveryManPlus.shop.model.dto.UpdateRequestDto;
import com.deliveryManPlus.shop.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner/shop")
@RequiredArgsConstructor
public class ShopForOwnerController {
    private final ShopService shopService;
    private final SessionValidator sessionValidator;
    @PostMapping
    public ResponseEntity<Void> create(@SessionAttribute(name = SessionConst.SESSION_KEY) Authentication auth,
                                       @Valid @RequestBody CreateRequestDto dto) {
        shopService.create(auth.getId(), dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<ShopDetailResponseDto> updateShop(@SessionAttribute(name = SessionConst.SESSION_KEY) Authentication auth,
                                                            @PathVariable Long shopId,
                                                            @Valid @RequestBody UpdateRequestDto dto) {

        ShopDetailResponseDto responseDto =shopService.updateShop(shopId,auth.getId(), dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopDetailResponseDto> updateStatus(@SessionAttribute(name = SessionConst.SESSION_KEY) Authentication auth,
                                                              @PathVariable Long shopId,
                                                              @Valid @RequestBody ShopStatusRequestDto status) {
        ShopDetailResponseDto dto = shopService.updateShopStatus(shopId, auth.getId(), status.getStatus());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @DeleteMapping("/{shopId}")
    public ResponseEntity<Void> delete(@SessionAttribute(name = SessionConst.SESSION_KEY) Authentication auth,
                                                              @PathVariable Long shopId) {
        shopService.deleteShop(shopId, auth.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
