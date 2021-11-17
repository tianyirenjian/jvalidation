package com.tianyisoft.jvalidate.aops;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianyisoft.jvalidate.annotations.JValidate;
import com.tianyisoft.jvalidate.annotations.JValidated;
import com.tianyisoft.jvalidate.annotations.NeedDatabase;
import com.tianyisoft.jvalidate.exceptions.ValidateFailedException;
import com.tianyisoft.jvalidate.utils.Tuple2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
        Map<String, Object> errors = doValidate(collectAnnotationParameters(joinPoint, args));
        System.out.println(errors);
        if (errors.size() > 0) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(errors);
            throw new ValidateFailedException(HttpStatus.BAD_REQUEST, json);
        }
        return joinPoint.proceed(args);
    }

    private Map<Integer, Object> collectAnnotationParameters(ProceedingJoinPoint joinPoint, Object[] args) {
        Method method = ((MethodSignature)(joinPoint.getSignature())).getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Map<Integer, Object> needValidate = new HashMap<>();
        for (int i = 0; i < method.getParameterCount(); i++) {
            if (Arrays.stream(parameterAnnotations[i]).anyMatch(annotation -> annotation instanceof JValidated)) {
                needValidate.put(i, args[i]);
            }
        }
        return needValidate;
    }

    private Map<String, Object> doValidate(Map<Integer, Object> parameters) {
        Map<String, Object> errors = new HashMap<>();
        parameters.forEach((index, parameter) -> {
            Class<?> klass = parameter.getClass();
            Arrays.stream(klass.getDeclaredFields()).forEach(field -> {
                List<String> errorList = new ArrayList<>();
                Arrays.stream(field.getAnnotations()).filter(annotation -> {
                    return Arrays.stream(annotation.annotationType().getAnnotations()).anyMatch(anno -> anno instanceof JValidate);
                }).forEachOrdered(annotation -> {
                    try {
                        Class<?> clazz = Class.forName(annotation.annotationType().getName().replaceFirst(".annotations.", ".validators.") + "Validator");
                        Object validatorInstance = clazz.newInstance();
                        Object result = null;
                        if (annotation.annotationType().isAnnotationPresent(NeedDatabase.class)) {
                            Method method = clazz.getMethod("validate", annotation.annotationType(), JdbcTemplate.class, Class.class, Object.class, String.class);
                            result = method.invoke(validatorInstance, annotation, jdbcTemplate, klass, parameter, field.getName());
                        } else {
                            Method method = clazz.getMethod("validate", annotation.annotationType(), Class.class, Object.class, String.class);
                            result = method.invoke(validatorInstance, annotation, klass, parameter, field.getName());
                        }
                        Tuple2<Boolean, String> tuple2 = Tuple2.castFrom(result);
                        if (!tuple2.getV0()) {
                            errorList.add(tuple2.getV1());
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        e.printStackTrace();
                    }
                });
                if (errorList.size() > 0) {
                    errors.put(field.getName(), errorList);
                }
            });
        });
        return errors;
    }
}
