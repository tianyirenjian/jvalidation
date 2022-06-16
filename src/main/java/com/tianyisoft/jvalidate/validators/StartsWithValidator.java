package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.StartsWith;
import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class StartsWithValidator extends Validator {
    public Tuple2<Boolean, String> validate(StartsWith startsWith, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), startsWith.groups(), startsWith.condition(), vParams.getKlass(), vParams.getObject(), startsWith.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
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
            return falseResult(vParams.getMessages(), startsWith.message(), mapOf(
                    Pair.of("attribute", vParams.getFieldName()),
                    Pair.of("input", o),
                    Pair.of("starts", Arrays.toString(startsWith.starts()))
            ));
        }
    }
}
