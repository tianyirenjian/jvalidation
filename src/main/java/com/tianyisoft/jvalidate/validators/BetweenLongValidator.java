package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.BetweenLong;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class BetweenLongValidator extends BaseBetweenNumberValidator {
    public Tuple2<Boolean, String> validate(BetweenLong betweenLong, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, betweenLong.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateNumber(o, Long.class, betweenLong.min(), betweenLong.max(), betweenLong.message(), fieldName);
    }
}
