package com.deliveryManPlus.auth.model.dto;


import com.deliveryManPlus.auth.model.entity.BasicAuth;
import com.deliveryManPlus.user.constant.Role;
import com.deliveryManPlus.user.model.entity.User;
import jakarta.validation.constraints.AssertTrue;
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

    public BasicAuth toBasicAuthEntity() {
        return new BasicAuth(email, password);
    }

    public User toUserEntity() {
        return new User(nickname, birthday, Role.valueOf(role.toUpperCase()));
    }
}
