package com.tianyisoft.jvalidate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianyisoft.jvalidate.annotations.Bail;
import com.tianyisoft.jvalidate.annotations.JValidate;
import com.tianyisoft.jvalidate.annotations.NeedDatabase;
import com.tianyisoft.jvalidate.utils.Helper;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class JValidator {
    public static Map<String, List<String>> validate(Object object, Class<?>[] groups, JdbcTemplate jdbcTemplate) {
        return validate(object, groups, jdbcTemplate, "zh_CN", "zh_CN");
    }

    public static Map<String, List<String>> validate(Object object, Class<?>[] groups) {
        return validate(object, groups, null, "zh_CN", "zh_CN");
    }

    public static Map<String, List<String>> validate(Object object, Class<?>[] groups,JdbcTemplate jdbcTemplate, String language, String defaultLang) {
        Map<String, Object> map = new HashMap<>();
        map.put("object", object);
        map.put("groups", groups);
        return doValidate(map, jdbcTemplate, language, defaultLang);
    }

    public static Map<String, List<String>> doValidate(Map<String, Object> parameters, JdbcTemplate jdbcTemplate, String language, String defaultLang) {
        Map<String, String> messages = messages(language, defaultLang);
        Map<String, List<String>> errors = new HashMap<>();
        Object parameter = parameters.get("object");
        Class<?>[] groups = (Class<?>[]) parameters.get("groups");

        Class<?> klass = parameter.getClass();
        Arrays.stream(klass.getDeclaredFields()).forEach(field -> {
            List<String> errorList = new ArrayList<>();
            List<Annotation> annotations = Arrays.stream(field.getAnnotations()).filter(annotation -> {
                return Arrays.stream(annotation.annotationType().getAnnotations()).anyMatch(anno -> anno instanceof JValidate);
            }).collect(Collectors.toList());
            boolean bail = false;
            for (Annotation annotation: annotations) {
                if (annotation.annotationType() == Bail.class) {
                    bail = true;
                    continue;
                }
                try {
                    Class<?> clazz = Class.forName(annotation.annotationType().getName().replaceFirst(".annotations.", ".validators.") + "Validator");
                    Object validatorInstance = clazz.newInstance();
                    Object result = null;
                    ValidatorParams params;
                    if (annotation.annotationType().isAnnotationPresent(NeedDatabase.class)) {
                        params = new ValidatorParams(groups, jdbcTemplate, klass, parameter, field.getName());
                    } else {
                        params = new ValidatorParams(groups, null, klass, parameter, field.getName());
                    }
                    Method method = clazz.getMethod("validate", annotation.annotationType(), ValidatorParams.class);
                    result = method.invoke(validatorInstance, annotation, params);

                    Tuple2<Boolean, String> tuple2 = Tuple2.castFrom(result);
                    if (!tuple2.getV0()) {
                        errorList.add(tuple2.getV1());
                        if (bail) {
                            break;
                        }
                    }
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
            if (errorList.size() > 0) {
                errors.put(field.getName(), errorList);
            }
        });
        return errors;
    }

    private static Map<String, String> messages(String lang, String defaultLang) {
        if (Objects.equals(lang, "zh")) {
            lang = "zh_CN";
        }
        Map<String, String> messages = new HashMap<>();
        try {
            String values = Helper.readResourceFile("/jvalidation." + lang + ".json", StandardCharsets.UTF_8);
            messages = (new ObjectMapper()).readValue(values, new TypeReference<Map<String, String>>() {});
        } catch(IOException ex) {
            try {
                String values = Helper.readResourceFile("/jvalidation." + defaultLang + ".json", StandardCharsets.UTF_8);
                messages = (new ObjectMapper()).readValue(values, new TypeReference<Map<String, String>>() {});
            } catch (IOException exception) {
                return messages;
            }
        }
        return messages;
    }
}
