package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Alpha;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class AlphaValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(Alpha alpha, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, alpha.groups(), alpha.condition(), klass, object, alpha.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateRegexp(o, regexp() , 0, alpha.message(), fieldName);
    }

    @Override
    public String regexp() {
        return "^[a-zA-Z]*$";
    }
}
