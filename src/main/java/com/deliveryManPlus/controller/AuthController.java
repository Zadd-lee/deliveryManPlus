package com.deliveryManPlus.controller;

import com.deliveryManPlus.dto.auth.JwtAuthResponseDto;
import com.deliveryManPlus.dto.auth.LoginRequestDto;
import com.deliveryManPlus.dto.auth.SigninRequestDto;
import com.deliveryManPlus.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증/인가", description = "로그인, 로그아웃, 회원가입 API")
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "회원가입", description = "회원가입을 진행합니다."
            , tags = {"인증/인가"}, responses = {
        @ApiResponse(responseCode = "201", description = "회원가입 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/signin")
    public ResponseEntity<Void> signin(@Valid @RequestBody SigninRequestDto dto) {
        authService.signin(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "로그인", description = "로그인을 진행합니다."
            , tags = {"인증/인가"},responses = {
        @ApiResponse(responseCode = "200", description = "로그인 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "401", description = "인증 실패"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        return new ResponseEntity<>(authService.login(dto), HttpStatus.OK);
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다."
            , tags = {"인증/인가"},responses = {
        @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
        @ApiResponse(responseCode = "401", description = "로그인 필요"),
        @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request,
                                       HttpServletResponse response, Authentication authentication)
            throws UsernameNotFoundException {
        // 인증 정보가 있다면 로그아웃 처리.
        if (authentication != null && authentication.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        // 인증 정보가 없다면 인증되지 않았기 때문에 로그인 필요.
        throw new UsernameNotFoundException("로그인이 먼저 필요합니다.");
    }
}
