package com.deliveryManPlus.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    @Schema(description = "사용자의 이메일", example = "oneBeen1234@deliveryMan.com")
    @NotBlank
    @Email
    private String email;
    @Schema(description = "사용자의 비밀번호", example = "oneBeen1234")
    @NotBlank
    private String password;
}
