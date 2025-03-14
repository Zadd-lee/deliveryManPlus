package com.deliveryManPlus.auth.dto;


import com.deliveryManPlus.auth.entity.BasicAuth;
import com.deliveryManPlus.auth.constant.Role;
import com.deliveryManPlus.auth.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "회원가입 요청 DTO")
@Getter
@NoArgsConstructor
public class SigninRequestDto {
    @Schema(description = "사용자의 별명", example = "One-Been")
    @NotBlank
    private String nickname;
    @Schema(description = "사용자의 생년월일", example = "1990-01-01",maxLength = 10)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate birthday;
    @Schema(description = "사용자의 역할",allowableValues = {"ADMIN","OWNER","CUSTOMER"}, example = "ADMIN")
    @NotNull
    private String role;

    @Schema(description = "사용자의 이메일", example = "oneBeen1234@deliveryMan.com")
    @Email
    private String email;
    @Schema(description = "사용자의 비밀번호", example = "oneBeen1234")
    @NotBlank
    private String password;

    public BasicAuth toBasicAuthEntity(String encodedPassword) {
        return new BasicAuth(this.email, encodedPassword);
    }

    public User toUserEntity() {
        return new User(nickname, birthday, Role.valueOf(role.toUpperCase()));
    }
}
