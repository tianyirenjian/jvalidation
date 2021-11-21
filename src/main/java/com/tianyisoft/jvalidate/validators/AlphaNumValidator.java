package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.AlphaNum;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class AlphaNumValidator extends Validator {
    public Tuple2<Boolean, String> validate(AlphaNum alphaNum, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, alphaNum.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null || (o instanceof String && ((String) o).matches(getRegexp()))) {
            return trueResult();
        }
        return falseResult(alphaNum.message(), fieldName);
    }

    public String getRegexp() {
        return "^[a-zA-Z0-9]*$";
    }
}
