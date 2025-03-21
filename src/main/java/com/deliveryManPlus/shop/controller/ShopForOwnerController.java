package com.deliveryManPlus.shop.controller;

import com.deliveryManPlus.shop.dto.ShopCreateRequestDto;
import com.deliveryManPlus.shop.dto.ShopDetailResponseDto;
import com.deliveryManPlus.shop.dto.ShopStatusRequestDto;
import com.deliveryManPlus.shop.dto.ShopUpdateRequestDto;
import com.deliveryManPlus.shop.service.ShopService;
import com.deliveryManPlus.auth.config.UserDetailsImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/owner/shop")
@RequiredArgsConstructor
public class ShopForOwnerController {
    private final ShopService shopService;
    @Operation(summary = "상점 생성", description = "상점을 생성합니다."
            , tags = {"Shop"}
            ,responses = {
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "카테고리 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<Void> create(@AuthenticationPrincipal UserDetailsImp userDetailsImp,
                                       @Valid @RequestPart("dto") ShopCreateRequestDto dto,
                                       @RequestPart("imageList") List<MultipartFile> imageList) {
        shopService.create(userDetailsImp.getBasicAuth().getUser(), dto, imageList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "상점 수정", description = "상점을 수정합니다."
            , tags = {"Shop"}
            ,responses = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "카테고리 없음"),
            @ApiResponse(responseCode = "404", description = "상점 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/{shopId}")
    public ResponseEntity<ShopDetailResponseDto> updateShop(@Parameter(name = "상점id"
            ,description = "상점 식별자",in = ParameterIn.PATH,required = true,example = "1") @PathVariable(name = "shopId") Long shopId,
                                                            @Valid @RequestBody ShopUpdateRequestDto dto) {

        ShopDetailResponseDto responseDto =shopService.updateShop(shopId, dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "상점 상태 수정", description = "상점의 상태를 수정합니다."
            , tags = {"Shop"}
            ,responses = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "상점 없음"),
            @ApiResponse(responseCode = "406", description = "상태 변경 불가"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }
    ,parameters = {
            @Parameter(name = "shopId", description = "상점 식별자", required = true, example = "1")
    })
    @PatchMapping("/{shopId}")
    public ResponseEntity<ShopDetailResponseDto> updateStatus(@PathVariable(name = "shopId") Long shopId,
                                                              @Valid @RequestBody ShopStatusRequestDto status) {
        ShopDetailResponseDto dto = shopService.updateShopStatus(shopId, status.getStatus());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @Operation(summary = "상점 삭제", description = "상점을 삭제합니다."
            , tags = {"Shop"}
            ,responses = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "상점 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }, parameters = {
            @Parameter(name = "shopId", description = "상점 식별자", required = true, example = "1")
    })
    @DeleteMapping("/{shopId}")
    public ResponseEntity<Void> delete(@PathVariable(name = "shopId") Long shopId) {
        shopService.deleteShop(shopId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
