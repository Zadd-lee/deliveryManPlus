package com.deliveryManPlus.auth.config;

import com.deliveryManPlus.auth.filter.LogInFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
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

/*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthInterceptor)
                .addPathPatterns(ADMIN_AUTH_REQUIRED_PATH_PATTERNS)
                .order(2);

        registry.addInterceptor(userAuthInterceptor)
                .addPathPatterns(USER_AUTH_REQUIRED_PATH_PATTERNS)
                .order(3);

        registry.addInterceptor(ownerAuthInterceptor)
                .addPathPatterns(OWNER_AUTH_REQUIRED_PATH_PATTERNS)
                .order(4);
}*/

}
