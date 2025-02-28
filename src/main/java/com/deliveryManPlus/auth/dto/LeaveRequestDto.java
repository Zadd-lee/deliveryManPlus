package com.deliveryManPlus.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LeaveRequestDto {
    @NotBlank
    private String password;
}
