package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Regexp;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.util.regex.Pattern;

public class RegexpValidator extends Validator {
    public Tuple2<Boolean, String> validate(Regexp regexp, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, regexp.groups(), regexp.condition(), klass, object, regexp.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateRegexp(o, regexp.rule(), regexp.flags(), regexp.message(), fieldName);
    }

    protected Tuple2<Boolean, String> validateRegexp(Object o, String rule, int flags, String message, String fieldName) {
        if (o == null) {
            return trueResult();
        }
        if (o instanceof String) {
            if (regexpValidate((String) o, rule, flags)) {
                return trueResult();
            }
        }
        return falseResult(message, fieldName);
    }

    protected Boolean regexpValidate(String text, String rule, int flags) {
        return Pattern.compile(rule, flags).matcher(text).matches();
    }

    protected String regexp() {
        return "";
    }
}
