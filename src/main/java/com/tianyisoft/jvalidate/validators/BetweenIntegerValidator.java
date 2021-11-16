package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.BetweenInteger;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class BetweenIntegerValidator extends BaseBetweenNumberValidator {
    public Tuple2<Boolean, String> validate(BetweenInteger betweenInteger, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Object o = getFieldValue(klass, object, fieldName);
        return validateNumber(o, Integer.class, betweenInteger.min(), betweenInteger.max(), betweenInteger.message(), fieldName);
    }
}
