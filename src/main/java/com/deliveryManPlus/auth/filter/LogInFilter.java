package com.deliveryManPlus.auth.filter;

import com.deliveryManPlus.auth.constant.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class LogInFilter implements Filter {

    //로그인 필터 화이트 리스트
    private static final String[] WHITE_LIST = {"/", "/auth/signup", "/auth/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

//        try{
            if (!isWhiteList(requestURI)) {
                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute(SessionConst.SESSION_KEY) == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
                }
            }
            chain.doFilter(request, response);
        /*}catch (ApiException e){
            httpResponse.setStatus(e.getErrorCode().getHttpStatus().value());
            httpResponse.setContentType("application/json;charset=UTF-8");

            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(e.getErrorCode().name())
                    .message(e.getErrorCode().getMessage())
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            httpResponse.getWriter().write(jsonResponse);
        }*/

    }

    //화이트 리스트 검증 메서드
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
