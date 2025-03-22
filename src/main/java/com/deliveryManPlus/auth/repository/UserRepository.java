package com.deliveryManPlus.auth.repository;

import com.deliveryManPlus.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
