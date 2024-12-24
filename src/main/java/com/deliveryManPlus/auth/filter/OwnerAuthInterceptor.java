package com.deliveryManPlus.auth.filter;

import com.deliveryManPlus.auth.constant.SessionConst;
import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.user.constant.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class OwnerAuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Authentication auth = (Authentication) request.getSession().getAttribute(SessionConst.SESSION_KEY);
        if (auth.getRole()!= Role.OWNER) {

            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); // 406 에러
            return false; // 요청을 중단
        }

        return true; // 요청을 진행
    }

}
