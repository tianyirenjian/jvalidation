package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.StartsWith;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class StartsWithValidator extends Validator {
    public Tuple2<Boolean, String> validate(StartsWith startsWith, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, startsWith.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (!(o instanceof String)) {
            return trueResult();
        }
        AtomicBoolean end = new AtomicBoolean(false);
        Arrays.stream(startsWith.starts()).forEach(s -> {
            if (((String) o).startsWith(s)) {
                end.set(true);
            }
        });
        if (end.get()) {
            return trueResult();
        } else {
            return falseResult(startsWith.message(), fieldName, String.join(", ", Arrays.toString(startsWith.starts())));
        }
    }
}
