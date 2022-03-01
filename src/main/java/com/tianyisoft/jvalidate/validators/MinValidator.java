package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Min;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

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
        return falseResult(min.message(), vParams.getFieldName(), number);
    }
}
