package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Regexp;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.regex.Pattern;

public class RegexpValidator extends Validator {
    public Tuple2<Boolean, String> validate(Regexp regexp, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), regexp.groups(), regexp.condition(), vParams.getKlass(), vParams.getObject(), regexp.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        return validateRegexp(o, regexp.rule(), regexp.flags(), regexp.message(), vParams.getFieldName());
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
