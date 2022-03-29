package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.RequiredIf;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class RequiredIfValidator extends RequiredValidator {
    public Tuple2<Boolean, String> validate(RequiredIf requiredIf, ValidatorParams vParams)
            throws InstantiationException, IllegalAccessException, NoSuchFieldException {

        if (notNeedValidate(vParams.getGroups(), requiredIf.groups(), requiredIf.condition(), vParams.getKlass(), vParams.getObject(), requiredIf.params())) {
            return trueResult();
        }

        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        return validateRequired(o, requiredIf.allowEmpty(), requiredIf.allowBlank(), requiredIf.message(), vParams);
    }
}
