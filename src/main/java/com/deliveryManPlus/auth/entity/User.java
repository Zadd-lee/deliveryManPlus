package com.deliveryManPlus.auth.entity;

import com.deliveryManPlus.auth.constant.Role;
import com.deliveryManPlus.cart.entity.Cart;
import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.common.entity.CreateAndUpdateDateEntity;
import com.deliveryManPlus.review.entity.Review;
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

    @Column(nullable = false)
    private String nickname;
    private LocalDate birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate canceledDate;

    @OneToMany(mappedBy = "user")
    List<BasicAuth> basicAuthList;

    @OneToMany(mappedBy = "customer")
    List<Cart> cartList;

    @OneToMany(mappedBy = "customer")
    List<Review> reviewList;

    public User(String nickname, LocalDate birthday, Role role) {
        this.nickname = nickname;
        this.birthday = birthday;
        this.role = role;
        this.status = Status.USE;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void leave() {
        this.status = Status.DELETED;
        this.canceledDate = LocalDate.now();
    }
}
