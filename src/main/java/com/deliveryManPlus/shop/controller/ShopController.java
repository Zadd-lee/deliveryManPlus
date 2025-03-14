package com.deliveryManPlus.shop.controller;

import com.deliveryManPlus.shop.dto.ShopDetailResponseDto;
import com.deliveryManPlus.shop.dto.ShopResponseDto;
import com.deliveryManPlus.shop.dto.ShopSearchOptionDto;
import com.deliveryManPlus.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Shop", description = "상점 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    @Operation(summary = "모든 상점 조회", description = "모든 상점을 조회합니다."
            , tags = {"Shop"}
            ,responses = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ResponseEntity<Page<ShopResponseDto>> findAll(@Valid @RequestBody ShopSearchOptionDto dto,
                                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "10") int size){
        return new ResponseEntity<>(shopService.findAll(dto,page,size), HttpStatus.OK);
    }

    @Operation(summary = "상점 조회", description = "상점을 조회합니다."
            , tags = {"Shop"}
    ,responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "상점 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }
    ,parameters = {
            @Parameter(name = "shopId", description = "상점 식별자", required = true, example = "1")
    })
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDetailResponseDto> findById(@PathVariable(name = "shopId") Long shopId){
        ShopDetailResponseDto dto = shopService.findById(shopId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
