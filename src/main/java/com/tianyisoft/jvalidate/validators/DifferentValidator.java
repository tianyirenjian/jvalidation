package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Different;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.util.Objects;

public class DifferentValidator extends Validator {
    public Tuple2<Boolean, String> validate(Different different, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, different.groups(), different.condition(), klass, object, different.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        Object o2 = getFieldValue(klass, object, different.field());
        if (different.strict()) {
            return o == o2 ? falseResult(different.message(), fieldName, different.field()) : trueResult();
        } else {
            return Objects.equals(o, o2) ?falseResult(different.message(), fieldName, different.field()) : trueResult();
        }
    }
}
