package com.deliveryManPlus.auth.service.imp;

import com.deliveryManPlus.auth.model.dto.SigninRequestDto;
import com.deliveryManPlus.auth.model.entity.BasicAuth;
import com.deliveryManPlus.auth.repository.AuthRepository;
import com.deliveryManPlus.user.repository.UserRepository;
import com.deliveryManPlus.auth.service.AuthService;
import com.deliveryManPlus.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class authServiceImp implements AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    @Override
    public void signin(SigninRequestDto dto) {

        //user 저장
        User user = dto.toUserEntity();
        userRepository.save(user);

        //basicAuth 저장
        BasicAuth basicAuth = dto.toBasicAuthEntity();
        basicAuth.updateUser(user);
        authRepository.save(basicAuth);

    }
}
