package com.exchange.filter;

import com.exchange.utils.JwtHelper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final JwtHelper jwtHelper;

    FilterConfig(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Bean
    public FilterRegistrationBean<JwtRequestFilter> loggingFilter() {
        FilterRegistrationBean<JwtRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtRequestFilter(jwtHelper));

        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}