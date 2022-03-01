package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.AfterOrEqual;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class AfterOrEqualValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(AfterOrEqual afterOrEqual, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), afterOrEqual.groups(), afterOrEqual.condition(), vParams.getKlass(), vParams.getObject(), afterOrEqual.params())) {
            return trueResult();
        }
        return validateDate(vParams.getKlass(), vParams.getObject(), vParams.getFieldName(), afterOrEqual.withTime(), afterOrEqual.date(), afterOrEqual.message(), "gte");
    }
}
