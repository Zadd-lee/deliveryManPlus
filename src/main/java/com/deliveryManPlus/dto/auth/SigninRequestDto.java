package com.deliveryManPlus.dto.auth;


import com.deliveryManPlus.entity.BasicAuth;
import com.deliveryManPlus.constant.Role;
import com.deliveryManPlus.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SigninRequestDto {
    @NotBlank
    private String nickname;
    @NotNull
    private LocalDate birthday;
    @NotNull
    private String role;

    @Email
    private String email;
    @NotBlank
    private String password;

    public BasicAuth toBasicAuthEntity(String encodedPassword) {
        return new BasicAuth(this.email, encodedPassword);
    }

    public User toUserEntity() {
        return new User(nickname, birthday, Role.valueOf(role.toUpperCase()));
    }
}
