package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.AlphaNum;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class AlphaNumValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(AlphaNum alphaNum, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, alphaNum.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateRegexp(o, regexp() , 0, alphaNum.message(), fieldName);
    }

    @Override
    public String regexp() {
        return "^[a-zA-Z0-9]*$";
    }
}
