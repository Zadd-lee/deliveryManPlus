package com.deliveryManPlus.user.model.entity;

import com.deliveryManPlus.model.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.user.constant.Role;
import com.deliveryManPlus.user.constant.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
public class User extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nickname;
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String nickname, LocalDate birthday, Role role) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.role = role;
        this.status = Status.USE;
    }
}
