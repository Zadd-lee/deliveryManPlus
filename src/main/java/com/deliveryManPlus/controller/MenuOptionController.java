package com.deliveryManPlus.controller;

import com.deliveryManPlus.dto.menuOption.MenuOptionRequestDto;
import com.deliveryManPlus.service.MenuOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MenuOption", description = "메뉴 옵션 API")
@RestController
@RequestMapping("/owner/{shopId}/menu/{menuId}/menuOption")
@RequiredArgsConstructor
public class MenuOptionController {
    private final MenuOptionService service;

    @Operation(summary = "메뉴 옵션 생성", description = "메뉴 옵션을 생성합니다."
    ,responses = {
        @ApiResponse(responseCode = "201", description = "메뉴 옵션 생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없음"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    }
    ,parameters = {
        @Parameter(name = "shopId", description = "가게 ID", required = true, example = "1"),
        @Parameter(name = "menuId", description = "메뉴 ID", required = true, example = "1")
    })
    @PostMapping
    public ResponseEntity<Void> createMenuOptions(@PathVariable(name = "shopId") Long shopId
            , @PathVariable("menuId") Long menuId
            , @RequestBody List<MenuOptionRequestDto> dtoList) {
        service.createMenuOptions(shopId, menuId, dtoList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "메뉴 옵션 전체 삭제", description = "메뉴 옵션을 전체 삭제합니다."
            ,responses = {
            @ApiResponse(responseCode = "200", description = "메뉴 옵션 전체 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "가게를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    }, parameters = {
        @Parameter(name = "shopId", description = "가게 ID", required = true, example = "1"),
        @Parameter(name = "menuId", description = "메뉴 ID", required = true, example = "1")
    })
    @DeleteMapping
    public ResponseEntity<String> deleteAllByMenuId(@PathVariable(name = "shopId") Long shopId
            , @PathVariable("menuId") Long menuId) {
        service.deleteById(menuId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
