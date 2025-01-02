package com.deliveryManPlus.auth.config;

import com.deliveryManPlus.auth.filter.*;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.deliveryManPlus.auth.constant.UrlConst.*;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final SessionValidationInterceptor sessionValidationInterceptor;
    private final AdminAuthInterceptor adminAuthInterceptor;
    private final OwnerAuthInterceptor ownerAuthInterceptor;
    private final CustomerAuthInterceptor customerAuthInterceptor;

    /**
     * 로그인 필터 등록
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean loginFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogInFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    /**
     * 인터셉터 등록
     *
     * @param registry
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(sessionValidationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(WHITE_LIST)
                .order(1);

        registry.addInterceptor(adminAuthInterceptor)
                .addPathPatterns(ADMIN_INTERCEPTOR_LIST)
                .order(2);

        registry.addInterceptor(ownerAuthInterceptor)
                .addPathPatterns(OWNER_INTERCEPTOR_LIST)
                .order(3);

        registry.addInterceptor(customerAuthInterceptor)
                .addPathPatterns(CUSTOMER_INTERCEPTOR_LIST)
                .order(4);

}

}
