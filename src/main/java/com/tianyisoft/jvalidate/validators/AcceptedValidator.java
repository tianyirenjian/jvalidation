package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Accepted;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.util.Arrays;
import java.util.List;

public class AcceptedValidator extends Validator {
    public Tuple2<Boolean, String> validate(Accepted accepted, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, accepted.groups(), accepted.condition(), klass, object, accepted.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        List<String> acceptedValues = Arrays.asList("on", "yes", "1", "true");
        if (o == null || (o instanceof String && (acceptedValues.contains(o)))) {
            return trueResult();
        }
        return falseResult(accepted.message(), fieldName);
    }
}
