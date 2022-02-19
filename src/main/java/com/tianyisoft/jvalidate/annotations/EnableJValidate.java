package com.tianyisoft.jvalidate.annotations;

import com.tianyisoft.jvalidate.configurations.BindingErrorsArgumentResolverConfiguration;
import com.tianyisoft.jvalidate.configurations.JDoValidateInterceptorConfiguration;
import com.tianyisoft.jvalidate.configurations.JValidationConfiguration;
import com.tianyisoft.jvalidate.configurations.ReadableRequestFilterConfiguration;
import com.tianyisoft.jvalidate.exceptions.ValidateFailedExceptionHandler;
import com.tianyisoft.jvalidate.filters.ReadableRequestFilter;
import com.tianyisoft.jvalidate.interceptors.JDoValidateInterceptor;
import com.tianyisoft.jvalidate.resolvers.BindingErrorsArgumentResolver;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于启用 JValidation，放到 spring boot 启动类或配置类上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
        JValidationConfiguration.class,
        ValidateFailedExceptionHandler.class,
        ReadableRequestFilter.class, ReadableRequestFilterConfiguration.class,
        JDoValidateInterceptor.class, JDoValidateInterceptorConfiguration.class,
        BindingErrorsArgumentResolver.class, BindingErrorsArgumentResolverConfiguration.class
})
public @interface EnableJValidate {
}
