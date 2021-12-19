package com.tianyisoft.jvalidate.aops;

import com.tianyisoft.jvalidate.JValidator;
import com.tianyisoft.jvalidate.annotations.JValidated;
import com.tianyisoft.jvalidate.exceptions.ValidateFailedException;
import com.tianyisoft.jvalidate.utils.BindingErrors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class JDoValidate {
    private final JdbcTemplate jdbcTemplate;

    public JDoValidate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Around("@annotation(com.tianyisoft.jvalidate.annotations.JValidated)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Map<String, List<String>> errors = JValidator.doValidate(collectAnnotationParameters(joinPoint, args), jdbcTemplate);
        if (errors.size() > 0) {
            boolean hasBindingErrors = false;
            for (Object arg : args) {
                if (arg instanceof BindingErrors) {
                    hasBindingErrors = true;
                    ((BindingErrors) arg).setErrors(errors);
                    break;
                }
            }
            if (!hasBindingErrors) {
                throw new ValidateFailedException(HttpStatus.UNPROCESSABLE_ENTITY, errors);
            }
        }
        return joinPoint.proceed(args);
    }

    private Map<Object, Class<?>[]> collectAnnotationParameters(ProceedingJoinPoint joinPoint, Object[] args) {
        Method method = ((MethodSignature)(joinPoint.getSignature())).getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Map<Object, Class<?>[]> needValidate = new HashMap<>();
        for (int i = 0; i < method.getParameterCount(); i++) {
            for (Annotation annotation: parameterAnnotations[i]) {
                if (annotation instanceof JValidated) {
                    needValidate.put(args[i], ((JValidated) annotation).groups());
                }
            }
        }
        return needValidate;
    }
}
