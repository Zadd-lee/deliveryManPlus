package com.deliveryManPlus.auth.entity;

import com.deliveryManPlus.auth.constant.Role;
import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.common.entity.CreateAndUpdateDateEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    List<BasicAuth> basicAuthList;

    @OneToMany(mappedBy = "customer")
    List<Cart> cartList;

    public User(String nickname, LocalDate birthday, Role role) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.role = role;
        this.status = Status.USE;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
