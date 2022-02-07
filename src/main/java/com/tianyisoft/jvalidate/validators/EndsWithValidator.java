package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.EndsWith;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class EndsWithValidator extends Validator {
    public Tuple2<Boolean, String> validate(EndsWith endsWith, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, endsWith.groups(), endsWith.condition(), klass, object, endsWith.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (!(o instanceof String)) {
            return trueResult();
        }
        AtomicBoolean end = new AtomicBoolean(false);
        Arrays.stream(endsWith.ends()).forEach(s -> {
            if (((String) o).endsWith(s)) {
                end.set(true);
            }
        });
        if (end.get()) {
            return trueResult();
        } else {
            return falseResult(endsWith.message(), fieldName, String.join(", ", Arrays.toString(endsWith.ends())));
        }
    }
}
