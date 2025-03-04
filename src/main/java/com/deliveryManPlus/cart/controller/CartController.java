package com.deliveryManPlus.cart.controller;

import com.deliveryManPlus.cart.dto.CartCreateMenuDto;
import com.deliveryManPlus.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/shop/{shopId}/menu/{menuId}/cart")
    public ResponseEntity<Void> addCartMenu(@PathVariable(name = "shopId") Long shopId, @PathVariable(name = "menuId") Long menuId, @RequestBody CartCreateMenuDto dto) {
        cartService.addCartMenu(shopId, menuId, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
