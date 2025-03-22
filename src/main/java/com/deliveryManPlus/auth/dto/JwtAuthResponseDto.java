package com.deliveryManPlus.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@AllArgsConstructor
public class JwtAuthResponseDto {

  @Schema(description = "토큰 인증 스키마")
  private String tokenAuthScheme;
  @Schema(description = "액세스 토큰")
  private String accessToken;
  @Schema(description = "리프레시 토큰")
  private String refreshToken;

}
