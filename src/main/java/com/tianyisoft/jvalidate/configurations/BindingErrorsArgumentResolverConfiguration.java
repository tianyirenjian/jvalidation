package com.tianyisoft.jvalidate.configurations;

import com.tianyisoft.jvalidate.resolvers.BindingErrorsArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class BindingErrorsArgumentResolverConfiguration implements WebMvcConfigurer {
    private final BindingErrorsArgumentResolver bindingErrorsArgumentResolver;

    public BindingErrorsArgumentResolverConfiguration(BindingErrorsArgumentResolver bindingErrorsArgumentResolver) {
        this.bindingErrorsArgumentResolver = bindingErrorsArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(bindingErrorsArgumentResolver);
    }
}
