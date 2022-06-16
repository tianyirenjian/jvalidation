package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.BeforeOrEqual;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class BeforeOrEqualValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(BeforeOrEqual beforeOrEqual, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), beforeOrEqual.groups(), beforeOrEqual.condition(), vParams.getKlass(), vParams.getObject(), beforeOrEqual.params())) {
            return trueResult();
        }
        return validateDate(vParams, beforeOrEqual.withTime(), beforeOrEqual.date(), beforeOrEqual.message(), "lte");
    }
}
