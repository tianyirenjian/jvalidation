package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.AlphaDash;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class AlphaDashValidator extends Validator {
    public Tuple2<Boolean, String> validate(AlphaDash alphaDash, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, alphaDash.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null || (o instanceof String && ((String) o).matches(getRegexp()))) {
            return trueResult();
        }
        return falseResult(alphaDash.message(), fieldName);
    }

    public String getRegexp() {
        return "^[a-zA-Z0-9_-]*$";
    }
}
