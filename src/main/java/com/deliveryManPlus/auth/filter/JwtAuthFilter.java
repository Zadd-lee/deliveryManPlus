package com.deliveryManPlus.auth.filter;

import com.deliveryManPlus.auth.utils.AuthenticationScheme;
import com.deliveryManPlus.auth.utils.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "Security::JwtAuthFilter")
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("URI: {}", request.getRequestURI());
        this.authenticate(request);
        filterChain.doFilter(request, response);
    }

    private void authenticate(HttpServletRequest request) {
        log.info("인증 처리.");


        //토큰 검증
        String token = this.getTokenFromRequest(request);
        if (!jwtProvider.validToken(token)) {
            return;
        }

        //토큰에서 유저 정보 추출
        String username = jwtProvider.getUsername(token);

        //userName을 갖는 유저를 꺼냄
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);


        this.setAuthentication(request,userDetails);



    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {

        // 찾아온 사용자 정보로 인증 객체를 생성
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // SecurityContext에 인증 객체 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 헤더에서 토큰을 추출한다.
     * @param request
     * @return {없을 경우 null 리턴}
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String headerPrefix = AuthenticationScheme.generateType(AuthenticationScheme.BEARER);

        boolean tokenFound =
                StringUtils.hasText(bearerToken) && bearerToken.startsWith(headerPrefix);
        if (tokenFound) {
            return bearerToken.substring(headerPrefix.length());
        }

        return null;
    }
}
