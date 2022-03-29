package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.AlphaNum;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class AlphaNumValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(AlphaNum alphaNum, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), alphaNum.groups(), alphaNum.condition(), vParams.getKlass(), vParams.getObject(), alphaNum.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        return validateRegexp(o, regexp() , 0, alphaNum.message(), vParams);
    }

    @Override
    public String regexp() {
        return "^[a-zA-Z0-9]*$";
    }
}
