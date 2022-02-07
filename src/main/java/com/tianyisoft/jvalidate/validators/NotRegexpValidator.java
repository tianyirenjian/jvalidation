package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.NotRegexp;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class NotRegexpValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(NotRegexp notRegexp, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, InstantiationException, IllegalAccessException {

        if (notNeedValidate(groups, notRegexp.groups(), notRegexp.condition(), klass, object, notRegexp.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateNotRegexp(o, notRegexp.rule(), notRegexp.flags(), notRegexp.message(), fieldName);
    }

    protected Tuple2<Boolean, String> validateNotRegexp(Object o, String rule, int flags, String message, String fieldName) {
        if (o == null) {
            return trueResult();
        }
        if (o instanceof String) {
            if (regexpValidate((String) o, rule, flags)) {
                return falseResult(message, fieldName);
            }
        }
        return trueResult();
    }
}
