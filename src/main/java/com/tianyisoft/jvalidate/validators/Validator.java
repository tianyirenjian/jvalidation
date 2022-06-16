package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.Condition;
import com.tianyisoft.jvalidate.utils.Tuple2;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Validator {
    public Boolean needValidateByGroups(Class<?>[] group1, Class<?>[] group2) {
        if (group1.length == 0) {
            return group2.length == 0;
        }
        if (group2.length == 0) {
            return true;
        }
        Set<Class<?>> set1 = new HashSet<>(Arrays.asList(group1));
        Set<Class<?>> set2 = new HashSet<>(Arrays.asList(group2));
        set1.retainAll(set2);
        return set1.size() > 0;
    }

    public Boolean notNeedValidateByGroups(Class<?>[] group1, Class<?>[] group2) {
        return !needValidateByGroups(group1, group2);
    }

    public Object getFieldValue(Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = klass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    public Tuple2<Boolean, String> trueResult() {
        return new Tuple2<>(true, "");
    }

    public Tuple2<Boolean, String> falseResult(String message, Object ...params) {
        return new Tuple2<>(false, String.format(message, params));
    }

    public Tuple2<Boolean, String> falseResult(Map<String, String> messages, String messageKey, Map<String, Object> replaces) {
        return new Tuple2<>(false, getErrorMessage(messages, messageKey, replaces));
    }

    public Field getAnotherField(Class<?> klass, String name) {
        List<Field> fields = Arrays.stream(klass.getDeclaredFields()).filter(field -> field.getName().equals(name)).collect(Collectors.toList());
        if (fields.size() == 1) {
            return fields.get(0);
        }
        return null;
    }

    // request.get.id, request.path.id, request.header.id
    public Object getValueFromRequest(String path) {
        String[] paths = path.split("\\.");
        if (paths.length != 3) {
            return null;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        switch (paths[1]) {
            case "get":
                return getRequestGetParameter(request, paths[2]);
            case "path":
                return getRequestPathParameter(request, paths[2]);
            case "header":
            case "headers":
                return getRequestHeaderParameter(request, paths[2]);
            default:
                return null;
        }
    }

    private Object getRequestGetParameter(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        return value != null ? value : "";
    }

    @SuppressWarnings("unchecked")
    private Object getRequestPathParameter(HttpServletRequest request, String key) {
        Map<String, Object> attributes = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return attributes.getOrDefault(key, "");
    }

    private Object getRequestHeaderParameter(HttpServletRequest request, String key) {
        String value = request.getHeader(key);
        return value != null ? value : "";
    }

    protected Tuple2<String, List<Object>> explainWhere(Class<?> klass, Object object, String where) throws NoSuchFieldException, IllegalAccessException {
        Pattern pattern = Pattern.compile("\\{\\{\\s*([a-zA-Z_][a-zA-Z0-9_.]*)\\s*}}");
        Matcher matcher = pattern.matcher(where);
        List<Object> parameters = new ArrayList<>();
        while (matcher.find()) {
            where = where.replaceFirst(pattern.pattern(), "?");
            String field = matcher.group(1);
            Object value = null;
            if (field.startsWith("request.")) {
                value = getValueFromRequest(field);
            } else {
                value = getFieldValue(klass, object, field);
            }
            parameters.add(value);
        }
        where = where.replace("\\{\\{", "{{");
        return new Tuple2<>(where, parameters);
    }

    protected Object[] conditionParameters(Class<?> klass, Object object, String[] params) throws NoSuchFieldException, IllegalAccessException {
        List<Object> objects = new ArrayList<>();
        for (String value : params) {
            if (value.startsWith("{{") && value.endsWith("}}")) {
                value = value.substring(2, value.length() - 2).trim();
                Object val = null;
                if (value.equals("this")) {
                    val = object;
                } else if (value.startsWith("request.")) {
                    val = getValueFromRequest(value);
                } else {
                    val = getFieldValue(klass, object, value);
                }
                objects.add(val);
            } else {
                objects.add(value);
            }
        }
        return objects.toArray(new Object[0]);
    }

    protected Boolean notNeedValidateByCondition(Class<? extends Condition> condition, Class<?> klass, Object object, String[] params) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        if (condition.equals(Condition.class)) {
            return false;
        }
        Condition instance = condition.newInstance();
        return !instance.needValidate(conditionParameters(klass, object, params));
    }

    protected Boolean notNeedValidate(Class<?>[] group1, Class<?>[] group2, Class<? extends Condition> condition, Class<?> klass, Object object, String[] params) throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        return notNeedValidateByGroups(group1, group2) || notNeedValidateByCondition(condition, klass, object, params);
    }

    protected Object tryDoubleToLong(Double d) {
        Object od = d;
        if (d == Math.floor(d)) {
            od = ((Number) d).longValue();
        }
        return od;
    }

    protected String getErrorMessage(Map<String, String> messages, String messageKey, Map<String, Object> replaces) {
        final String[] message = {messages.getOrDefault(messageKey, messageKey)};
        replaces.forEach((k, v) -> {
            message[0] = message[0].replaceAll(":" +k+ ":", String.format("%s", v instanceof Double ? tryDoubleToLong((Double)v) : v));
        });
        return message[0];
    }
}
