package com.deliveryManPlus.auth.constant;
//todo : url 상수화 리팩터링
public class UrlConst {

    //로그인 필터 화이트 리스트
    public static final String[] WHITE_LIST = {"/", "/auth/signin", "/auth/login", "/shop", "/shop/*"
    ,"/api*", "/api-docs/**", "/swagger-ui/**"};

    //사장 인터셉터 리스트
    public static final String[] ADMIN_INTERCEPTOR_LIST = {"/admin", "/admin/**"};

    //사장 인터셉터 리스트
    public static final String[] OWNER_INTERCEPTOR_LIST = {"/owner", "/owner/**"};

    //손님 인터셉터 리스트
    public static final String[] CUSTOMER_INTERCEPTOR_LIST = {"/user", "/user/**"};

}
