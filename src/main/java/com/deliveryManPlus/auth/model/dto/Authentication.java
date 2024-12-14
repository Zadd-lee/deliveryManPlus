package com.deliveryManPlus.auth.model.dto;

import com.deliveryManPlus.auth.model.entity.BasicAuth;
import com.deliveryManPlus.user.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class Authentication {
    private final Long id;
    private final String email;
    private final Role role;

    public Authentication(BasicAuth basicAuth) {
        this.id = basicAuth.getUser().getId();
        this.email=basicAuth.getEmail();
        this.role=basicAuth.getUser().getRole();
    }
}
