package com.deliveryManPlus.auth.service.imp;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.auth.model.dto.LoginRequestDto;
import com.deliveryManPlus.auth.model.dto.SigninRequestDto;
import com.deliveryManPlus.auth.model.entity.BasicAuth;
import com.deliveryManPlus.auth.repository.AuthRepository;
import com.deliveryManPlus.user.repository.UserRepository;
import com.deliveryManPlus.auth.service.AuthService;
import com.deliveryManPlus.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

    @Override
    public Authentication login(LoginRequestDto dto) {
        //검증
        BasicAuth basicAuth = authRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));


        return new Authentication(basicAuth);
    }
}
