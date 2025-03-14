package com.deliveryManPlus.auth.controller;

import com.deliveryManPlus.auth.dto.*;
import com.deliveryManPlus.auth.service.AuthService;
import com.deliveryManPlus.auth.utils.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "로그인, 로그아웃, 회원가입 API")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;


    @Operation(summary = "회원가입", description = "회원가입을 진행합니다."
            , responses = {
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
            , responses = {
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
            , responses = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 필요"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){

        authService.logout(jwtTokenProvider.resolveAccessToken(request));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Void> leave(@Valid @RequestBody LeaveRequestDto leaveRequestDto) {
        authService.leave(leaveRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "토큰 갱신", description = "토큰을 갱신합니다."
            , responses = {
            @ApiResponse(responseCode = "200", description = "갱신 성공"),
            @ApiResponse(responseCode = "401", description = "토큰 만료"),
            @ApiResponse(responseCode = "403", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "로그인 되지 않은 사용자"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponseDto> refresh(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(authService.refreshToken(tokenRequestDto), HttpStatus.OK);
    }
}