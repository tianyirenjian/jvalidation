package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.Condition;
import com.tianyisoft.jvalidate.annotations.RequiredIf;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class RequiredIfValidator extends RequiredValidator {
    public Tuple2<Boolean, String> validate(RequiredIf requiredIf, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        if (!needValidateByGroups(groups, requiredIf.groups())) {
            return trueResult();
        }
        Condition condition = requiredIf.condition().newInstance();
        if (condition.needValidate(parameters(klass, object, requiredIf.params()))) {
            Object o = getFieldValue(klass, object, fieldName);
            return validateRequired(o, requiredIf.allowEmpty(), requiredIf.message(), fieldName);
        }
        return trueResult();
    }

    private Object[] parameters(Class<?> klass, Object object, String[] params) throws NoSuchFieldException, IllegalAccessException {
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
}
