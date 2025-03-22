package com.deliveryManPlus.cart.controller;

import com.deliveryManPlus.cart.dto.CartMenuOptionDetailRequestDto;
import com.deliveryManPlus.cart.dto.CartMenuQuantityRequestDto;
import com.deliveryManPlus.cart.dto.CartMenuRequestDto;
import com.deliveryManPlus.cart.dto.CartResponseDto;
import com.deliveryManPlus.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/shop/{shopId}/menu/{menuId}/cart")
    public ResponseEntity<Void> addCartMenu(@PathVariable(name = "shopId") Long shopId
            , @PathVariable(name = "menuId") Long menuId
            , @RequestBody CartMenuRequestDto dto) {
        cartService.addCartMenu(shopId, menuId, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/cart")
    public ResponseEntity<CartResponseDto> getCartList() {
        return new ResponseEntity<>(cartService.findCartList(), HttpStatus.OK);
    }

    @PutMapping("/user/cart/menu/{menuId}")
    public ResponseEntity<Void> updateCartMenu(@PathVariable(name = "menuId") Long menuId
            , @RequestBody CartMenuOptionDetailRequestDto dto) {
        cartService.updateCartMenuOptionDetail(menuId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/user/cart/menu/{menuId}")
    public ResponseEntity<Void> deleteCartMenu(@PathVariable(name = "menuId") Long menuId
    ,@RequestBody CartMenuQuantityRequestDto dto) {
        cartService.updateCartMenuQuantity(menuId,dto.getQuantity());
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @DeleteMapping("/user/cart/menu/{menuId}")
    public ResponseEntity<Void> deleteCartMenu(@PathVariable(name = "menuId") Long menuId) {
        cartService.deleteCartMenu(menuId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/user/cart")
    public ResponseEntity<Void> deleteCart() {
        cartService.deleteCart();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
