package com.deliveryManPlus.auth.service.imp;

import com.deliveryManPlus.auth.constant.UserPolicy;
import com.deliveryManPlus.auth.dto.*;
import com.deliveryManPlus.auth.entity.BasicAuth;
import com.deliveryManPlus.auth.entity.RefreshToken;
import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.auth.repository.AuthRepository;
import com.deliveryManPlus.auth.repository.RefreshTokenRepository;
import com.deliveryManPlus.auth.repository.UserRepository;
import com.deliveryManPlus.auth.service.AuthService;
import com.deliveryManPlus.auth.utils.JwtTokenProvider;
import com.deliveryManPlus.common.constant.Status;
import com.deliveryManPlus.common.exception.ApiException;
import com.deliveryManPlus.common.exception.constant.errorcode.AuthErrorCode;
import com.deliveryManPlus.common.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void signin(SigninRequestDto dto) {
        //검증
        Optional<BasicAuth> authByEmail = authRepository.findByEmail(dto.getEmail());
        if(authByEmail.isPresent()){
            User user = authByEmail.get().getUser();
            if(user.getStatus().equals(Status.USE)){
                throw new ApiException(AuthErrorCode.EXIST_USER);
            }
            if(!(user.getStatus().equals(Status.DELETED) && isCorrectPolicy(user))){
                throw new ApiException(AuthErrorCode.CANCELED_USER);
            }
        }

        //user 저장
        User user = dto.toUserEntity();
        userRepository.save(user);

        //basicAuth 저장
        BasicAuth basicAuth = dto.toBasicAuthEntity(passwordEncoder.encode(dto.getPassword()));
        basicAuth.updateUser(user);

        authRepository.save(basicAuth);

    }

    private boolean isCorrectPolicy(User user) {
        return user.getCanceledDate().isBefore(LocalDate.now().minusMonths(UserPolicy.RESIGNIN_MONTHS.getMonths()));
    }

    @Override
    public JwtAuthResponseDto login(LoginRequestDto dto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        // 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtAuthResponseDto tokenDto = jwtTokenProvider.generateToken(authentication);

        RefreshToken refreshToken = new RefreshToken(authentication.getName(), tokenDto.getRefreshToken());
        refreshTokenRepository.save(refreshToken);

        return tokenDto;

    }

    @Override
    public void leave(LeaveRequestDto dto) {
        //검증
        User user = SecurityUtils.getUser();
        BasicAuth basicAuth = authRepository.findByUserOrElseThrow(user);

        if(!basicAuth.getPassword().equals(dto.getPassword())){
            throw new ApiException(AuthErrorCode.PASSWORD_INCORRECT);
        }
        
        basicAuth.getUser().leave();
    }

    @Override
    public JwtAuthResponseDto refreshToken(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // 5. 새로운 토큰 생성
        JwtAuthResponseDto jwtAuthResponseDto = null;
        if (jwtTokenProvider.refreshTokenPeriodCheck(refreshToken.getValue())) {
            // 5-1. Refresh Token의 유효기간이 3일 미만일 경우 전체(Access / Refresh) 재발급
            jwtAuthResponseDto = jwtTokenProvider.generateToken(authentication);

            // 6. Refresh Token 저장소 정보 업데이트
            RefreshToken newRefreshToken = refreshToken.updateValue(jwtAuthResponseDto.getRefreshToken());
            refreshTokenRepository.save(newRefreshToken);
        } else {
            // 5-2. Refresh Token의 유효기간이 3일 이상일 경우 Access Token만 재발급
            jwtAuthResponseDto = jwtTokenProvider.createAccessToken(authentication);
        }

        // 토큰 발급
        return jwtAuthResponseDto;
    }


    @Override
    public void logout(String accessToken) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 4. Refresh Token 삭제
        refreshTokenRepository.delete(refreshToken);

        // 5. SecurityContext 에서 인증 정보 삭제
        SecurityContextHolder.clearContext();

    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
