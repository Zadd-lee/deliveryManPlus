package com.deliveryManPlus.auth.controller;

import com.deliveryManPlus.auth.constant.SessionConst;
import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.auth.model.dto.LeaveRequestDto;
import com.deliveryManPlus.auth.model.dto.LoginRequestDto;
import com.deliveryManPlus.auth.model.dto.SigninRequestDto;
import com.deliveryManPlus.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signin")
    public ResponseEntity<Void> signin(@Valid @RequestBody SigninRequestDto dto) {
        authService.signin(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto dto, HttpServletRequest request) {
        Authentication authentication = authService.login(dto);

        //session 생성
        HttpSession session = request.getSession(true);

        //session에 값 담음
        session.setAttribute( SessionConst.SESSION_KEY,authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        //만료
        request.getSession().invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/leave")
    public ResponseEntity<Void> leave(@Valid @RequestBody LeaveRequestDto dto,
                                      @SessionAttribute(name = SessionConst.SESSION_KEY) Authentication authentication) {
        authService.leave(dto, authentication.getId());

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
