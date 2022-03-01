package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.After;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class AfterValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(After after, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), after.groups(), after.condition(), vParams.getKlass(), vParams.getObject(), after.params())) {
            return trueResult();
        }
        return validateDate(vParams.getKlass(), vParams.getObject(), vParams.getFieldName(), after.withTime(), after.date(), after.message(), "gt");
    }
}
