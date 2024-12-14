package com.deliveryManPlus.auth.repository;

import com.deliveryManPlus.auth.model.entity.BasicAuth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<BasicAuth,Long> {
    Optional<BasicAuth> findByEmailAndPassword( String email, String password);
}
