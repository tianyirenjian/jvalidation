package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Different;
import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.Objects;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class DifferentValidator extends Validator {
    public Tuple2<Boolean, String> validate(Different different, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), different.groups(), different.condition(), vParams.getKlass(), vParams.getObject(), different.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        Object o2 = getFieldValue(vParams.getKlass(), vParams.getObject(), different.field());
        boolean same;
        if (different.strict()) {
            same = o == o2;
        } else {
            same = Objects.equals(o, o2);
        }
        return !same ?
                trueResult() :
                falseResult(vParams.getMessages(), different.message(), mapOf(
                        Pair.of("attribute", vParams.getFieldName()),
                        Pair.of("input", o),
                        Pair.of("field", different.field())
                ));
    }
}
