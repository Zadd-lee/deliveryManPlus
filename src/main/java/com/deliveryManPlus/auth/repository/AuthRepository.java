package com.deliveryManPlus.auth.repository;

import com.deliveryManPlus.auth.entity.BasicAuth;
import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.AuthErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<BasicAuth,Long> {

    @Query("select b from BasicAuth b where b.user = :user")
    Optional<BasicAuth> findByUser(User user);

    Optional<BasicAuth> findByEmail(String email);

    boolean existsByEmail(String email);

    default BasicAuth findByUserOrElseThrow(User user){
        return findByUser(user).orElseThrow(()->new ApiException(AuthErrorCode.NOT_FOUND));
    }

}
