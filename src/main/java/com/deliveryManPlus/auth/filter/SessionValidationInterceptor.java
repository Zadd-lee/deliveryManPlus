package com.deliveryManPlus.auth.filter;

import com.deliveryManPlus.auth.constant.SessionConst;
import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.common.utils.SessionValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class SessionValidationInterceptor implements HandlerInterceptor {

    private final SessionValidator sessionValidator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 인증/인가 로직
        HttpSession session = request.getSession();
        Authentication auth = (Authentication) session.getAttribute(SessionConst.SESSION_KEY);

        if (auth == null || sessionValidator.isSessionValid(auth)) {
            session.invalidate();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 에러
            return false; // 요청을 중단
        }

        return true; // 요청을 진행
    }

}
