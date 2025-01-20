package com.deliveryManPlus.entity;

import com.deliveryManPlus.constant.Role;
import com.deliveryManPlus.constant.Status;
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
