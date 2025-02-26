package com.deliveryManPlus.controller;

import com.deliveryManPlus.dto.category.CategoryCreateRequestDto;
import com.deliveryManPlus.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryCreateRequestDto dto) {
        categoryService.createCategory(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
