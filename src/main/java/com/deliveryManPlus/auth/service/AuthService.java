package com.deliveryManPlus.auth.service;

import com.deliveryManPlus.auth.dto.TokenRequestDto;
import com.deliveryManPlus.auth.dto.JwtAuthResponseDto;
import com.deliveryManPlus.auth.dto.LeaveRequestDto;
import com.deliveryManPlus.auth.dto.LoginRequestDto;
import com.deliveryManPlus.auth.dto.SigninRequestDto;
import jakarta.validation.Valid;

public interface AuthService {

    void signin(@Valid SigninRequestDto dto);

    JwtAuthResponseDto login(@Valid LoginRequestDto dto);

    void leave(LeaveRequestDto dto);

    JwtAuthResponseDto refreshToken(TokenRequestDto refreshToken);

    void logout(String accessToken);
}
