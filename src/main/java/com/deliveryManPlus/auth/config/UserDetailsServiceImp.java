package com.deliveryManPlus.auth.config;

import com.deliveryManPlus.auth.entity.BasicAuth;
import com.deliveryManPlus.auth.repository.AuthRepository;
import com.deliveryManPlus.common.exception.constant.errorcode.UserErrorCode;
import com.deliveryManPlus.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    private final AuthRepository authRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BasicAuth user = authRepository.findByEmail(username)
                .orElseThrow(() -> new ApiException(UserErrorCode.NOT_FOUND));

        return new UserDetailsImp(user);
    }
}
