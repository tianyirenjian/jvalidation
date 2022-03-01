package com.tianyisoft.jvalidate.interceptors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tianyisoft.jvalidate.JValidator;
import com.tianyisoft.jvalidate.annotations.JValidated;
import com.tianyisoft.jvalidate.configurations.JValidationConfiguration;
import com.tianyisoft.jvalidate.exceptions.ValidateFailedException;
import com.tianyisoft.jvalidate.utils.BindingErrors;
import com.tianyisoft.jvalidate.utils.Helper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.lang.reflect.Parameter;
import java.util.*;

@Component
public class JDoValidateInterceptor implements HandlerInterceptor, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private final JValidationConfiguration jValidationConfiguration;

    public JDoValidateInterceptor(JValidationConfiguration jValidationConfiguration) {
        this.jValidationConfiguration = jValidationConfiguration;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Parameter[] parameters = handlerMethod.getMethod().getParameters();
        Optional<Parameter> first = Arrays.stream(parameters)
                .filter(param -> param.getAnnotation(JValidated.class) != null)
                .findFirst();
        if (!first.isPresent()) {
            return true;
        }
        Parameter firstParameter = first.get();

        String json = StreamUtils.copyToString(request.getInputStream(), Helper.getCharset(request.getCharacterEncoding()));
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Map<String, Object> needValidate = new HashMap<>();
        JValidated jvalidated = firstParameter.getAnnotation(JValidated.class);
        try {
            needValidate.put("object", mapper.readValue(json, firstParameter.getType()));
            needValidate.put("groups", jvalidated.groups());
        } catch (JsonProcessingException e) {
            return true;
        }

        Locale locale = LocaleContextHolder.getLocale();

        Map<String, List<String>> errors = JValidator.doValidate(needValidate,
                getJdbcTemplate(jvalidated.datasourceName()),
                locale.getLanguage(),
                jValidationConfiguration.getDefaultLang()
        );

        if (errors.size() > 0) {
            boolean hasBindingErrors = false;
            for (Parameter param : parameters) {
                if (param.getType().getName().equals(BindingErrors.class.getName())) {
                    hasBindingErrors = true;
                    request.setAttribute("jvalidation_binding_errors", new BindingErrors(errors));
                    break;
                }
            }
            if (!hasBindingErrors) {
                throw new ValidateFailedException(HttpStatus.UNPROCESSABLE_ENTITY, errors);
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private JdbcTemplate getJdbcTemplate(String name) {
        String datasourceName = jValidationConfiguration.getDatasourceName();
        if (Helper.isEmptyOrNull(datasourceName)) {
            if (Helper.isEmptyOrNull(name)) { // 全局为空，自己也是空，用默认
                return (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
            } else { // 全局为空，自己不为空，用自己
                DataSource datasource = (DataSource) applicationContext.getBean(name);
                return new JdbcTemplate(datasource);
            }
        } else {
            DataSource datasource;
            if (Helper.isEmptyOrNull(name)) { // 全局不为空，自己为空，用全局
                datasource = (DataSource) applicationContext.getBean(datasourceName);
            } else { // 自己和全局都不为空，用自己
                datasource = (DataSource) applicationContext.getBean(name);
            }
            return new JdbcTemplate(datasource);
        }
    }
}
