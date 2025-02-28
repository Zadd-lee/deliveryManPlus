package com.deliveryManPlus.category.controller;

import com.deliveryManPlus.category.dto.CategoryCreateRequestDto;
import com.deliveryManPlus.category.dto.CategoryResponseDto;
import com.deliveryManPlus.category.dto.CategorySearchRequestDto;
import com.deliveryManPlus.category.dto.CategoryUpdateRequestDto;
import com.deliveryManPlus.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "카테고리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 생성", description = "카테고리를 생성합니다."
            , responses = {
            @ApiResponse(responseCode = "201", description = "카테고리 생성 성공"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 카테고리"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/admin/category")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryCreateRequestDto dto) {
        categoryService.createCategory(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "카테고리 조회", description = "카테고리를 검색 조건에 따라 조회합니다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "카테고리 조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/category")
    public ResponseEntity<List<CategoryResponseDto>> getCategoryList(@RequestBody CategorySearchRequestDto dto) {
        return new ResponseEntity<>(categoryService.getCategoryList(dto), HttpStatus.OK);
    }

    @Operation(summary = "카테고리 수정", description = "카테고리를 수정합니다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "카테고리 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/admin/category/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable(name = "categoryId") Long categoryId, @RequestBody CategoryUpdateRequestDto dto) {
        categoryService.updateCategory(categoryId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "카테고리 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
