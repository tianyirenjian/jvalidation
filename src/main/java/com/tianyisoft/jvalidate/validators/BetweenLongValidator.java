package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.BetweenLong;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class BetweenLongValidator extends BaseBetweenNumberValidator {
    public Tuple2<Boolean, String> validate(BetweenLong betweenLong, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Object o = getFieldValue(klass, object, fieldName);
        return validateNumber(o, Long.class, betweenLong.min(), betweenLong.max(), betweenLong.message(), fieldName);
    }
}
