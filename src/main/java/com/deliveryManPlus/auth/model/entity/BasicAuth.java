package com.deliveryManPlus.auth.model.entity;

import com.deliveryManPlus.model.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.user.model.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

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

    public BasicAuth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void updateUser(User user) {
        this.user = user;
    }
}
