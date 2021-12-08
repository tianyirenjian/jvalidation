package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Different;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class DifferentValidator extends Validator {
    public Tuple2<Boolean, String> validate(Different different, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, different.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        Object o2 = getFieldValue(klass, object, different.field());
        if (different.strict()) {
            if (o == o2) {
                return falseResult(different.message(), fieldName, different.field());
            } else {
                return trueResult();
            }
        } else {
            if (o.equals(o2)) {
                return falseResult(different.message(), fieldName, different.field());
            } else {
                return trueResult();
            }
        }
    }
}
