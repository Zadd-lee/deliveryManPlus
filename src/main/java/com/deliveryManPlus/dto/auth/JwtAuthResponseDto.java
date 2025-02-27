package com.deliveryManPlus.dto.auth;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@AllArgsConstructor
public class JwtAuthResponseDto {

  private String tokenAuthScheme;

  private String accessToken;

  private String refreshToken;

}
