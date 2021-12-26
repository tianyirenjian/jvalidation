package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Max;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class MaxValidator extends BaseLengthValidator {
    public Tuple2<Boolean, String> validate(Max max, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, max.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return trueResult();
        }

        if (validateLength("max", o, null, max.value())) {
            return trueResult();
        }

        Object number = tryDoubleToLong(max.value());
        return falseResult(max.message(), fieldName, number);
    }
}
