package com.deliveryManPlus.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LeaveRequestDto {
    @NotBlank
    private String password;
}
