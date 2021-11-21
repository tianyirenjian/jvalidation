package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.utils.Tuple2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public Field getAnotherField(Class<?> klass, String name) {
        List<Field> fields = Arrays.stream(klass.getDeclaredFields()).filter(field -> field.getName().equals(name)).collect(Collectors.toList());
        if (fields.size() == 1) {
            return fields.get(0);
        }
        return null;
    }
}
