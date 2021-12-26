package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Between;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class BetweenValidator extends BaseLengthValidator {
    public Tuple2<Boolean, String> validate(Between between, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, between.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return trueResult();
        }

        if (validateLength("between", o, between.min(), between.max())) {
            return trueResult();
        }

        Object min = tryDoubleToLong(between.min());
        Object max = tryDoubleToLong(between.max());
        return falseResult(between.message(), fieldName, min, max);
    }
}
