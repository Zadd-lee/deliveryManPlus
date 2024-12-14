package com.deliveryManPlus.auth.service;

import com.deliveryManPlus.auth.model.dto.SigninRequestDto;
import jakarta.validation.Valid;

public interface AuthService {

    void signin(@Valid SigninRequestDto dto);
}
