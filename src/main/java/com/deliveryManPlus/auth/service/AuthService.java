package com.deliveryManPlus.auth.service;

import com.deliveryManPlus.auth.model.dto.*;
import jakarta.validation.Valid;

public interface AuthService {

    void signin(@Valid SigninRequestDto dto);

    JwtAuthResponseDto login(@Valid LoginRequestDto dto);

    void leave(LeaveRequestDto dto, Long authentication);
}
