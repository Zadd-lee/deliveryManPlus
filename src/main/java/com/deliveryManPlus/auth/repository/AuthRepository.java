package com.deliveryManPlus.auth.repository;

import com.deliveryManPlus.auth.model.entity.BasicAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<BasicAuth,Long> {
}
