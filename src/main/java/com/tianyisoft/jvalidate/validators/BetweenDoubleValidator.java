package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.BetweenDouble;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class BetweenDoubleValidator extends BaseBetweenNumberValidator {
    public Tuple2<Boolean, String> validate(BetweenDouble betweenDouble, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, betweenDouble.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateNumber(o, Double.class, betweenDouble.min(), betweenDouble.max(), betweenDouble.message(), fieldName);
    }
}
