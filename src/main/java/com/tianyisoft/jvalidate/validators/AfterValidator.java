package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.After;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class AfterValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(After after, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, after.groups())) {
            return trueResult();
        }
        return validateDate(klass, object, fieldName, after.withTime(), after.date(), after.message(), "gt");
    }
}
