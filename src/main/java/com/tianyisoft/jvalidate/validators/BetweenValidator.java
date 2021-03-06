package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Between;
import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class BetweenValidator extends BaseLengthValidator {
    public Tuple2<Boolean, String> validate(Between between, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), between.groups(), between.condition(), vParams.getKlass(), vParams.getObject(), between.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        if (o == null) {
            return trueResult();
        }

        if (validateLength("between", o, between.min(), between.max())) {
            return trueResult();
        }

        Object min = tryDoubleToLong(between.min());
        Object max = tryDoubleToLong(between.max());
        return falseResult(vParams.getMessages(), between.message(), mapOf(
                Pair.of("attribute", vParams.getFieldName()),
                Pair.of("input", o),
                Pair.of("min", min),
                Pair.of("max", max)
        ));
    }
}
