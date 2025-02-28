package com.deliveryManPlus.controller;

import com.deliveryManPlus.dto.menu.*;
import com.deliveryManPlus.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu", description = "메뉴 API")
@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @Operation(summary = "메뉴 생성", description = "메뉴를 생성합니다."
    ,responses = {
        @ApiResponse(responseCode = "201", description = "메뉴 생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    }
    ,parameters = {
        @Parameter(name = "shopId", description = "가게 ID",in = ParameterIn.PATH, required = true, example = "1")
    })
    @PostMapping("/owner/{shopId}/menu")
    public ResponseEntity<Void> create(@PathVariable(name = "shopId") Long shopId,
                                       @Valid @RequestBody MenuCreateRequestDto dto) {
        menuService.create(shopId,dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "메뉴 수정", description = "메뉴를 수정합니다."
            ,responses = {
            @ApiResponse(responseCode = "200", description = "메뉴 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }
    , parameters = {
        @Parameter(name = "shopId", description = "가게 ID", required = true, example = "1"),
        @Parameter(name = "menuId", description = "메뉴 ID", required = true, example = "1")
    })
    @PutMapping("/owner/{shopId}/menu/{menuId}")
    public ResponseEntity<Void> update(@PathVariable Long shopId,
                                       @PathVariable Long menuId,
                                       @Valid @RequestBody MenuUpdateRequestDto dto) {
        menuService.update(shopId, menuId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "메뉴 상태 수정", description = "메뉴 상태를 수정합니다."
            ,responses = {
            @ApiResponse(responseCode = "200", description = "메뉴 상태 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }
    ,parameters = {
        @Parameter(name = "shopId", description = "가게 ID", required = true, example = "1"),
        @Parameter(name = "menuId", description = "메뉴 ID", required = true, example = "1")
    })
    @PatchMapping("/owner/{shopId}/menu/{menuId}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long shopId,
                                       @PathVariable Long menuId,
                                       @Valid @RequestBody MenuUpdateStatusRequestDto dto) {
        menuService.updateStatus(shopId, menuId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "메뉴 삭제", description = "메뉴를 삭제합니다."
            ,responses = {
            @ApiResponse(responseCode = "200", description = "메뉴 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }
    ,parameters = {
        @Parameter(name = "shopId", description = "가게 ID", required = true, example = "1"),
        @Parameter(name = "menuId", description = "메뉴 ID", required = true, example = "1")
    })
    @DeleteMapping("/owner/{shopId}/menu/{menuId}")
    public ResponseEntity<Void> delete(@PathVariable Long shopId,
                                       @PathVariable Long menuId) {
        menuService.delete(shopId, menuId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "메뉴 조회", description = "메뉴를 조회합니다."
            ,responses = {
            @ApiResponse(responseCode = "200", description = "메뉴 조회 성공"),
            @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없음"),
            @ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }, parameters = {
        @Parameter(name = "shopId", description = "가게 ID", required = true, example = "1"),
        @Parameter(name = "menuId", description = "메뉴 ID", required = true, example = "1")
    })
    @GetMapping("/shop/{shopId}/menu/{menuId}")
    public ResponseEntity<MenuDetailResponseDto> findById(@PathVariable(name = "shopId") Long shopId, @PathVariable(name = "menuId") Long menuId) {
        return new ResponseEntity<>(menuService.findById(shopId, menuId), HttpStatus.OK);
    }
}
