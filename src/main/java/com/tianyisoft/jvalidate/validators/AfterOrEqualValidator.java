package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.AfterOrEqual;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class AfterOrEqualValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(AfterOrEqual afterOrEqual, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, afterOrEqual.groups(), afterOrEqual.condition(), klass, object, afterOrEqual.params())) {
            return trueResult();
        }
        return validateDate(klass, object, fieldName, afterOrEqual.withTime(), afterOrEqual.date(), afterOrEqual.message(), "gte");
    }
}
