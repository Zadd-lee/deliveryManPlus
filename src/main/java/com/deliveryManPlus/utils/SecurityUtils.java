package com.deliveryManPlus.utils;

import com.deliveryManPlus.entity.User;
import com.deliveryManPlus.service.imp.UserDetailsImp;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static User getUser(){
        return getUserDetailsImp().getBasicAuth().getUser();
    }

    private static UserDetailsImp getUserDetailsImp() {
        return (UserDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
