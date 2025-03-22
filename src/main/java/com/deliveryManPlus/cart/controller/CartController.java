package com.deliveryManPlus.cart.controller;

import com.deliveryManPlus.cart.dto.CartMenuOptionDetailRequestDto;
import com.deliveryManPlus.cart.dto.CartMenuQuantityRequestDto;
import com.deliveryManPlus.cart.dto.CartMenuRequestDto;
import com.deliveryManPlus.cart.dto.CartResponseDto;
import com.deliveryManPlus.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart", description = "장바구니 API")
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @Operation(summary = "장바구니에 메뉴 추가", description = "장바구니에 메뉴를 추가합니다."
            , parameters = {
            @Parameter(name = "shopId", description = "가게 ID", required = true),
            @Parameter(name = "menuId", description = "메뉴 ID", required = true)
    }
            , responses = {
            @ApiResponse(responseCode = "201", description = "장바구니에 메뉴 추가 성공"),
            @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/shop/{shopId}/menu/{menuId}/cart")
    public ResponseEntity<Void> addCartMenu(@PathVariable(name = "shopId") Long shopId
            , @PathVariable(name = "menuId") Long menuId
            , @RequestBody CartMenuRequestDto dto) {
        cartService.addCartMenu(shopId, menuId, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "장바구니 조회", description = "장바구니를 조회합니다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "장바구니 조회 성공"),
            @ApiResponse(responseCode = "404", description = "장바구니를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/user/cart")
    public ResponseEntity<CartResponseDto> getCartList() {
        return new ResponseEntity<>(cartService.findCartList(), HttpStatus.OK);
    }

    @Operation(summary = "장바구니 메뉴 수정", description = "장바구니 메뉴를 수정합니다."
            , parameters = {
            @Parameter(name = "menuId", description = "메뉴 ID", required = true)
    }
            , responses = {
            @ApiResponse(responseCode = "200", description = "장바구니 메뉴 수정 성공"),
            @ApiResponse(responseCode = "404", description = "장바구니 안의 메뉴를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "400", description = "장바구니 메뉴는 수량 혹은 옵션만 수정 가능합니다"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/user/cart/menu/{menuId}")
    public ResponseEntity<Void> updateCartMenu(@PathVariable(name = "menuId") Long menuId
            , @RequestBody CartMenuOptionDetailRequestDto dto) {
        cartService.updateCartMenuOptionDetail(menuId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "장바구니 메뉴 수량 수정", description = "장바구니 메뉴 수량을 수정합니다."
            , parameters = {
            @Parameter(name = "menuId", description = "메뉴 ID", required = true)
    }
            , responses = {
            @ApiResponse(responseCode = "200", description = "장바구니 메뉴 수량 수정 성공"),
            @ApiResponse(responseCode = "404", description = "장바구니 안의 메뉴를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/user/cart/menu/{menuId}")
    public ResponseEntity<Void> updateCartMenuQuantity(@PathVariable(name = "menuId") Long menuId
            , @RequestBody CartMenuQuantityRequestDto dto) {
        cartService.updateCartMenuQuantity(menuId, dto.getQuantity());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "장바구니 메뉴 삭제", description = "장바구니 메뉴를 삭제합니다."
            , parameters = {
            @Parameter(name = "menuId", description = "메뉴 ID", required = true)
    }
            , responses = {
            @ApiResponse(responseCode = "204", description = "장바구니 메뉴 삭제 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/user/cart/menu/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable(name = "menuId") Long menuId) {
        cartService.deleteCartMenu(menuId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "장바구니 비우기", description = "장바구니를 비웁니다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "장바구니 비우기 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/user/cart")
    public ResponseEntity<Void> deleteCart() {
        cartService.deleteCart();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
