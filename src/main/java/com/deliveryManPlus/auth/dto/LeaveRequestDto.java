package com.deliveryManPlus.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LeaveRequestDto {
    @Schema(description = "사용자의 비밀번호", example = "oneBeen1234")
    @NotBlank
    private String password;
}
