package com.deliveryManPlus.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "토큰 요청 DTO")
@Getter
@NoArgsConstructor
public class TokenRequestDto {
        @Schema(description = "액세스 토큰")
        private String accessToken;
        @Schema(description = "리프레시 토큰")
        private String refreshToken;
}
