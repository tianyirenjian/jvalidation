package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Alpha;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class AlphaValidator extends Validator {
    public Tuple2<Boolean, String> validate(Alpha alpha, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null || (o instanceof String && ((String) o).matches(getRegexp()))) {
            return trueResult();
        }
        return falseResult(alpha.message(), fieldName);
    }

    public String getRegexp() {
        return "^[a-zA-Z]*$";
    }
}
