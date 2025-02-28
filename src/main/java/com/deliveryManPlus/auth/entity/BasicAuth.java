package com.deliveryManPlus.auth.entity;

import com.deliveryManPlus.common.entity.CreateAndUpdateDateEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class BasicAuth extends CreateAndUpdateDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BasicAuth(String email,String password) {
        this.email = email;
        this.password = password;
    }

    public void updateUser(User user) {
        this.user = user;
    }
}
