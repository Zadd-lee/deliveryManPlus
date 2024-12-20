package com.deliveryManPlus.menu.controller;

import com.deliveryManPlus.auth.constant.SessionConst;
import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.menu.model.dto.MenuCreateRequestDto;
import com.deliveryManPlus.menu.model.dto.MenuUpdateRequestDto;
import com.deliveryManPlus.menu.model.dto.MenuUpdateStatusRequestDto;
import com.deliveryManPlus.menu.service.MenuService;
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
    public ResponseEntity<Void> create(@SessionAttribute(name = SessionConst.SESSION_KEY) Authentication auth,
                                       @PathVariable Long shopId,
                                       @Valid @RequestBody MenuCreateRequestDto dto) {
        menuService.create(auth, shopId,dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<Void> update(@SessionAttribute(name = SessionConst.SESSION_KEY) Authentication auth,
                                       @PathVariable Long shopId,
                                       @PathVariable Long menuId,
                                       @Valid @RequestBody MenuUpdateRequestDto dto) {
        menuService.update(auth, shopId, menuId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<Void> updateStatus(@SessionAttribute(name = SessionConst.SESSION_KEY) Authentication auth,
                                       @PathVariable Long shopId,
                                       @PathVariable Long menuId,
                                       @Valid @RequestBody MenuUpdateStatusRequestDto dto) {
        menuService.updateStatus(auth, shopId, menuId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
