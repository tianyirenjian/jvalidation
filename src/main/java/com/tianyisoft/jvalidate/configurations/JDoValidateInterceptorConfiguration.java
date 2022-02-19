package com.tianyisoft.jvalidate.configurations;

import com.tianyisoft.jvalidate.interceptors.JDoValidateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JDoValidateInterceptorConfiguration implements WebMvcConfigurer {
    private final JDoValidateInterceptor jDoValidateInterceptor;

    public JDoValidateInterceptorConfiguration(JDoValidateInterceptor jDoValidateInterceptor) {
        this.jDoValidateInterceptor = jDoValidateInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(jDoValidateInterceptor);
        interceptorRegistration.addPathPatterns("/**").order(Integer.MAX_VALUE);
    }
}
