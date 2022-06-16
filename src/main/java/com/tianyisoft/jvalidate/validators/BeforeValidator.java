package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Before;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class BeforeValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(Before before, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), before.groups(), before.condition(), vParams.getKlass(), vParams.getObject(), before.params())) {
            return trueResult();
        }
        return validateDate(vParams, before.withTime(), before.date(), before.message(), "lt");
    }
}
