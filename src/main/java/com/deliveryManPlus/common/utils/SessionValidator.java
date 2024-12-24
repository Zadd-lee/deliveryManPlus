package com.deliveryManPlus.common.utils;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.user.model.entity.User;
import com.deliveryManPlus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SessionValidator {

    private final UserRepository userRepository;

    /**
     * 세션 유효성 검사
     * @param auth
     * @return
     */

    public boolean isSessionValid(Authentication auth) {
        Optional<User> user = userRepository.findById(auth.getId());
        if (user.isEmpty()) {
            return false;
        } else if (user.get().getRole() != auth.getRole()) {
            return false;
        }
        return user.get().getBasicAuthList().get(0).getEmail().equals(auth.getEmail());
    }
}
