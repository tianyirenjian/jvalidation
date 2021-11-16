package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.BetweenDouble;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class BetweenDoubleValidator extends BaseBetweenNumberValidator {
    public Tuple2<Boolean, String> validate(BetweenDouble betweenDouble, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Object o = getFieldValue(klass, object, fieldName);
        return validateNumber(o, Double.class, betweenDouble.min(), betweenDouble.max(), betweenDouble.message(), fieldName);
    }
}
