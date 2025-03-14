package com.deliveryManPlus.common.utils;

import com.deliveryManPlus.auth.entity.User;
import com.deliveryManPlus.auth.config.UserDetailsImp;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static User getUser(){
        return getUserDetailsImp().getBasicAuth().getUser();
    }

    private static UserDetailsImp getUserDetailsImp() {
        return (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
