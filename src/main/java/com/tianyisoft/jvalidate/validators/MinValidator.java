package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Min;
import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class MinValidator extends BaseLengthValidator {
    public Tuple2<Boolean, String> validate(Min min, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), min.groups(), min.condition(), vParams.getKlass(), vParams.getObject(), min.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        if (o == null) {
            return trueResult();
        }

        if (validateLength("min", o, min.value(), null)) {
            return trueResult();
        }

        Object number = tryDoubleToLong(min.value());
        return falseResult(vParams.getMessages(), min.message(), mapOf(
                Pair.of("attribute", vParams.getFieldName()),
                Pair.of("input", o),
                Pair.of("value", number)
        ));
    }
}
