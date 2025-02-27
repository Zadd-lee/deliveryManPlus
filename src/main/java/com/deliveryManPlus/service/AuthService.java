package com.deliveryManPlus.service;

import com.deliveryManPlus.dto.TokenRequestDto;
import com.deliveryManPlus.dto.auth.JwtAuthResponseDto;
import com.deliveryManPlus.dto.auth.LeaveRequestDto;
import com.deliveryManPlus.dto.auth.LoginRequestDto;
import com.deliveryManPlus.dto.auth.SigninRequestDto;
import jakarta.validation.Valid;

public interface AuthService {

    void signin(@Valid SigninRequestDto dto);

    JwtAuthResponseDto login(@Valid LoginRequestDto dto);

    void leave(LeaveRequestDto dto, Long authentication);

    JwtAuthResponseDto refreshToken(TokenRequestDto refreshToken);

    void logout(String accessToken);
}
