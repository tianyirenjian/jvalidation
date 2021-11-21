package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Required;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.lang.reflect.Field;

public class RequiredValidator extends Validator {
    public Tuple2<Boolean, String> validate(Required required, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, required.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return falseResult(required.message(), fieldName);
        }
        return trueResult();
    }
}
