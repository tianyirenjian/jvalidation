package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Max;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class MaxValidator extends BaseLengthValidator {
    public Tuple2<Boolean, String> validate(Max max, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), max.groups(), max.condition(), vParams.getKlass(), vParams.getObject(), max.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        if (o == null) {
            return trueResult();
        }

        if (validateLength("max", o, null, max.value())) {
            return trueResult();
        }

        Object number = tryDoubleToLong(max.value());
        return falseResult(max.message(), vParams.getFieldName(), number);
    }
}
