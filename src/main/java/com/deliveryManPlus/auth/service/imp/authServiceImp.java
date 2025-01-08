package com.deliveryManPlus.auth.service.imp;

import com.deliveryManPlus.auth.model.dto.JwtAuthResponseDto;
import com.deliveryManPlus.auth.model.dto.LeaveRequestDto;
import com.deliveryManPlus.auth.model.dto.LoginRequestDto;
import com.deliveryManPlus.auth.model.dto.SigninRequestDto;
import com.deliveryManPlus.auth.model.entity.BasicAuth;
import com.deliveryManPlus.auth.repository.AuthRepository;
import com.deliveryManPlus.auth.service.AuthService;
import com.deliveryManPlus.auth.utils.AuthenticationScheme;
import com.deliveryManPlus.auth.utils.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.deliveryManPlus.user.constant.Status;
import com.deliveryManPlus.user.model.entity.User;
import com.deliveryManPlus.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class authServiceImp implements AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signin(SigninRequestDto dto) {
        //검증
        Optional<BasicAuth> byEmail = authRepository.findByEmail(dto.getEmail());
        if(byEmail.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        //user 저장
        User user = dto.toUserEntity();
        userRepository.save(user);

        //basicAuth 저장
        BasicAuth basicAuth = dto.toBasicAuthEntity(passwordEncoder.encode(dto.getPassword()));
        basicAuth.updateUser(user);

        authRepository.save(basicAuth);

    }

    @Override
    public JwtAuthResponseDto login(LoginRequestDto dto) {
        //사용자 확인
        BasicAuth basicAuth = authRepository.findByEmail(dto.getEmail())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));
        validatePassword(dto.getPassword(), basicAuth.getPassword());

        //인증 객체를 저장
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );
        
        //security context에 저장
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        //토큰 생성
        String accessToken = jwtProvider.generateToken(auth);

        return new JwtAuthResponseDto(AuthenticationScheme.BEARER.getName(), accessToken);
    }

    @Override
    public void leave(LeaveRequestDto dto, Long userId) {
        //검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        user.setStatus(Status.CANCEL);
    }

    private void validatePassword(String rawPassword,String encodedPassword){
        if (!passwordEncoder.matches(rawPassword,encodedPassword)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
