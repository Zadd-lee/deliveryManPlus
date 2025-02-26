package com.deliveryManPlus.service.imp;

import com.deliveryManPlus.dto.auth.JwtAuthResponseDto;
import com.deliveryManPlus.dto.auth.LeaveRequestDto;
import com.deliveryManPlus.dto.auth.LoginRequestDto;
import com.deliveryManPlus.dto.auth.SigninRequestDto;
import com.deliveryManPlus.entity.BasicAuth;
import com.deliveryManPlus.repository.AuthRepository;
import com.deliveryManPlus.service.AuthService;
import com.deliveryManPlus.constant.AuthenticationScheme;
import com.deliveryManPlus.utils.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.deliveryManPlus.constant.Status;
import com.deliveryManPlus.entity.User;
import com.deliveryManPlus.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signin(SigninRequestDto dto) {
        //검증
        if(authRepository.existsByEmail(dto.getEmail())){
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

        user.setStatus(Status.DELETED);
    }

    private void validatePassword(String rawPassword,String encodedPassword){
        if (!passwordEncoder.matches(rawPassword,encodedPassword)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
