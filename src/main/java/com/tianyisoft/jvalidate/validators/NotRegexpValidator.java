package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.NotRegexp;
import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class NotRegexpValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(NotRegexp notRegexp, ValidatorParams vParams)
            throws NoSuchFieldException, InstantiationException, IllegalAccessException {

        if (notNeedValidate(vParams.getGroups(), notRegexp.groups(), notRegexp.condition(), vParams.getKlass(), vParams.getObject(), notRegexp.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        return validateNotRegexp(o, notRegexp.rule(), notRegexp.flags(), notRegexp.message(), vParams);
    }

    protected Tuple2<Boolean, String> validateNotRegexp(Object o, String rule, int flags, String message, ValidatorParams vParams) {
        if (o == null) {
            return trueResult();
        }
        if (o instanceof String) {
            if (regexpValidate((String) o, rule, flags)) {
                return falseResult(vParams.getMessages(), message, mapOf(
                        Pair.of("attribute", vParams.getFieldName()),
                        Pair.of("input", o),
                        Pair.of("rule", rule)
                ));
            }
        }
        return trueResult();
    }
}
