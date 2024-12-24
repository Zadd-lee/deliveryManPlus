package com.deliveryManPlus.auth.service;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.auth.model.dto.LeaveRequestDto;
import com.deliveryManPlus.auth.model.dto.LoginRequestDto;
import com.deliveryManPlus.auth.model.dto.SigninRequestDto;
import jakarta.validation.Valid;

public interface AuthService {

    void signin(@Valid SigninRequestDto dto);

    Authentication login(@Valid LoginRequestDto dto);

    void leave(LeaveRequestDto dto, Long authentication);
}
