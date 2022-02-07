package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.RequiredIf;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class RequiredIfValidator extends RequiredValidator {
    public Tuple2<Boolean, String> validate(RequiredIf requiredIf, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        if (notNeedValidate(groups, requiredIf.groups(), requiredIf.condition(), klass, object, requiredIf.params())) {
            return trueResult();
        }

        Object o = getFieldValue(klass, object, fieldName);
        return validateRequired(o, requiredIf.allowEmpty(), requiredIf.message(), fieldName);
    }
}
