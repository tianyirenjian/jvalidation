package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Min;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class MinValidator extends BaseLengthValidator {
    public Tuple2<Boolean, String> validate(Min min, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, min.groups(), min.condition(), klass, object, min.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return trueResult();
        }

        if (validateLength("min", o, min.value(), null)) {
            return trueResult();
        }

        Object number = tryDoubleToLong(min.value());
        return falseResult(min.message(), fieldName, number);
    }
}
