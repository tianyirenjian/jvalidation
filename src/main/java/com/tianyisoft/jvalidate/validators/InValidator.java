package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.In;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.util.Arrays;
import java.util.Objects;

public class InValidator extends Validator{
    public Tuple2<Boolean, String> validate(In in, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, in.groups(), in.condition(), klass, object, in.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (!(o instanceof String)) {
            return trueResult();
        }
        for (String value : in.values()) {
            if (Objects.equals(value, o)) {
                return trueResult();
            }
        }
        return falseResult(in.message(), fieldName, Arrays.toString(in.values()));
    }
}
