package com.deliveryManPlus.auth.service.imp;

import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.auth.model.dto.LeaveRequestDto;
import com.deliveryManPlus.auth.model.dto.LoginRequestDto;
import com.deliveryManPlus.auth.model.dto.SigninRequestDto;
import com.deliveryManPlus.auth.model.entity.BasicAuth;
import com.deliveryManPlus.auth.repository.AuthRepository;
import com.deliveryManPlus.user.constant.Status;
import com.deliveryManPlus.user.repository.UserRepository;
import com.deliveryManPlus.auth.service.AuthService;
import com.deliveryManPlus.user.model.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Service
@RequiredArgsConstructor
public class authServiceImp implements AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final BasicErrorController basicErrorController;

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

    @Override
    public void leave(LeaveRequestDto dto, Authentication authentication) {
        //검증
        User user = userRepository.findById(authentication.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        //todo 가입시 user와 basicAuth 둘 다 생성이 되나, 만약 user로 검색시 검색 결과가 없는 경우 서버 문제로 보는 것이 맞는가
        BasicAuth basicAuth = authRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        if (!basicAuth.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        user.setStatus(Status.CANCEL);
    }

    private static void validationWithSessionAndbasicAuth(Authentication authentication, BasicAuth basicAuth) {
        if(!isEqual(authentication, basicAuth)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private static boolean isEqual(Authentication authentication, BasicAuth basicAuth) {
        return !(basicAuth.getId().equals(authentication.getId()));
    }
}
