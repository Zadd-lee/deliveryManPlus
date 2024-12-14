package com.deliveryManPlus.auth.service.imp;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.auth.model.dto.LeaveRequestDto;
import com.deliveryManPlus.auth.model.dto.LoginRequestDto;
import com.deliveryManPlus.auth.model.dto.SigninRequestDto;
import com.deliveryManPlus.auth.model.entity.BasicAuth;
import com.deliveryManPlus.auth.repository.AuthRepository;
import com.deliveryManPlus.auth.service.AuthService;
import com.deliveryManPlus.auth.utils.PasswordEncoder;
import com.deliveryManPlus.user.constant.Status;
import com.deliveryManPlus.user.model.entity.User;
import com.deliveryManPlus.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class authServiceImp implements AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    @Override
    public void signin(SigninRequestDto dto) {
        //검증
        Optional<BasicAuth> byEmail = authRepository.findByEmail(dto.getEmail());
        if(byEmail.isPresent()){
            //todo 에러 처리
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

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
        BasicAuth basicAuth = authRepository.findByEmail(dto.getEmail())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));

        if (!PasswordEncoder.matches(dto.getPassword(), basicAuth.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        return new Authentication(basicAuth);
    }

    @Override
    public void leave(LeaveRequestDto dto, Authentication authentication) {
        //검증
        User user = userRepository.findById(authentication.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if (!PasswordEncoder.matches(dto.getPassword(),user.getBasicAuthList().get(0).getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        user.setStatus(Status.CANCEL);
    }
}
