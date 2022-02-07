package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.After;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class AfterValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(After after, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, after.groups(), after.condition(), klass, object, after.params())) {
            return trueResult();
        }
        return validateDate(klass, object, fieldName, after.withTime(), after.date(), after.message(), "gt");
    }
}
