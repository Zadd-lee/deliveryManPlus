package com.deliveryManPlus.auth.repository;

import com.deliveryManPlus.auth.model.entity.BasicAuth;
import com.deliveryManPlus.user.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<BasicAuth,Long> {
    Optional<BasicAuth> findByEmailAndPassword( String email, String password);

    @Query("select b from BasicAuth b where b.user = :user")
    Optional<BasicAuth> findByUser(User user);
}
