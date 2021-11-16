package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Before;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class BeforeValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(Before before, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        return validateDate(klass, object, fieldName, before.withTime(), before.date(), before.message(), "lt");
    }
}
