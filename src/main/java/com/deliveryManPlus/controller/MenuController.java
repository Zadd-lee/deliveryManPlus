package com.deliveryManPlus.controller;

import com.deliveryManPlus.dto.menu.MenuCreateRequestDto;
import com.deliveryManPlus.dto.menu.MenuUpdateRequestDto;
import com.deliveryManPlus.dto.menu.MenuUpdateStatusRequestDto;
import com.deliveryManPlus.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner/{shopId}/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable Long shopId,
                                       @Valid @RequestBody MenuCreateRequestDto dto) {
        menuService.create(shopId,dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<Void> update(@PathVariable Long shopId,
                                       @PathVariable Long menuId,
                                       @Valid @RequestBody MenuUpdateRequestDto dto) {
        menuService.update(shopId, menuId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long shopId,
                                       @PathVariable Long menuId,
                                       @Valid @RequestBody MenuUpdateStatusRequestDto dto) {
        menuService.updateStatus(shopId, menuId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> delete(@PathVariable Long shopId,
                                       @PathVariable Long menuId) {
        menuService.delete(shopId, menuId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
