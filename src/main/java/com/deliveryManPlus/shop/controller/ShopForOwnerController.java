package com.deliveryManPlus.shop.controller;

import com.deliveryManPlus.auth.constant.SessionConst;
import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.shop.model.dto.CreateRequestDto;
import com.deliveryManPlus.shop.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class ShopForOwnerController {
    private final ShopService shopService;
    @PostMapping("/shop")
    public ResponseEntity<Void> create(@SessionAttribute(name = SessionConst.SESSION_KEY)Authentication auth,
                                       @Valid @RequestBody CreateRequestDto dto){
        shopService.create(auth,dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
