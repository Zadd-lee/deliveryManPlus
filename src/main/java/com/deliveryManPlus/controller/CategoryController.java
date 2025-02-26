package com.deliveryManPlus.controller;

import com.deliveryManPlus.dto.category.CategoryRequestDto;
import com.deliveryManPlus.dto.category.CategoryResponseDto;
import com.deliveryManPlus.dto.category.CategorySearchRequestDto;
import com.deliveryManPlus.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequestDto dto) {
        categoryService.createCategory(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategoryList(@RequestBody CategorySearchRequestDto dto) {
        return new ResponseEntity<>(categoryService.getCategoryList(dto), HttpStatus.OK);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(@PathVariable(name = "categoryId") Long categoryId, @RequestBody CategoryRequestDto dto) {
        categoryService.updateCategory(categoryId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
