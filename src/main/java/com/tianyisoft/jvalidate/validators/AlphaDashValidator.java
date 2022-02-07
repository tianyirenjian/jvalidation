package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.AlphaDash;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class AlphaDashValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(AlphaDash alphaDash, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, alphaDash.groups(), alphaDash.condition(), klass, object, alphaDash.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateRegexp(o, regexp() , 0, alphaDash.message(), fieldName);
    }

    @Override
    public String regexp() {
        return "^[a-zA-Z0-9_-]*$";
    }
}
