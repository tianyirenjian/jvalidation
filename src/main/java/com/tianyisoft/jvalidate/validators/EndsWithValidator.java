package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.EndsWith;
import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class EndsWithValidator extends Validator {
    public Tuple2<Boolean, String> validate(EndsWith endsWith, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), endsWith.groups(), endsWith.condition(), vParams.getKlass(), vParams.getObject(), endsWith.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
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
            return falseResult(vParams.getMessages(), endsWith.message(), mapOf(
                    Pair.of("attribute", vParams.getFieldName()),
                    Pair.of("input", o),
                    Pair.of("ends", Arrays.toString(endsWith.ends()))
            ));
        }
    }
}
