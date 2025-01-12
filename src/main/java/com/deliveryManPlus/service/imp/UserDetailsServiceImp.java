package com.deliveryManPlus.service.imp;

import com.deliveryManPlus.entity.BasicAuth;
import com.deliveryManPlus.repository.AuthRepository;
import com.deliveryManPlus.constant.error.UserErrorCode;
import com.deliveryManPlus.exception.ApiException;
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
