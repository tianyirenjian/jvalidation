package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.BetweenString;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class BetweenStringValidator extends Validator {
    public Tuple2<Boolean, String> validate(BetweenString betweenString, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return trueResult();
        }
        if (o instanceof String) {
            String so = (String) o;
            if (so.length() >= betweenString.min() && so.length() <= betweenString.max()) {
                return trueResult();
            }
        }
        return falseResult(betweenString.message(), fieldName, betweenString.min(), betweenString.max());
    }
}
