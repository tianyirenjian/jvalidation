package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Alpha;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class AlphaValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(Alpha alpha, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), alpha.groups(), alpha.condition(), vParams.getKlass(), vParams.getObject(), alpha.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        return validateRegexp(o, regexp() , 0, alpha.message(), vParams);
    }

    @Override
    public String regexp() {
        return "^[a-zA-Z]*$";
    }
}
