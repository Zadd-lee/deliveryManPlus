package com.deliveryManPlus.controller;

import com.deliveryManPlus.dto.shop.ShopDetailResponseDto;
import com.deliveryManPlus.dto.shop.ShopResponseDto;
import com.deliveryManPlus.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<ShopResponseDto>> findAll(){
        List<ShopResponseDto> responseDtos = shopService.findAll();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
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
    public ResponseEntity<ShopDetailResponseDto> findById(@PathVariable Long shopId){
        ShopDetailResponseDto dto = shopService.findById(shopId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
