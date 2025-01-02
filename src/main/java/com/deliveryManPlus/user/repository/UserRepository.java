package com.deliveryManPlus.user.repository;

import com.deliveryManPlus.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
