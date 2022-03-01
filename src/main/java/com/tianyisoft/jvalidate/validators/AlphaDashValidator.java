package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.AlphaDash;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class AlphaDashValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(AlphaDash alphaDash, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), alphaDash.groups(), alphaDash.condition(), vParams.getKlass(), vParams.getObject(), alphaDash.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getClass(), vParams.getObject(), vParams.getFieldName());
        return validateRegexp(o, regexp() , 0, alphaDash.message(), vParams.getFieldName());
    }

    @Override
    public String regexp() {
        return "^[a-zA-Z0-9_-]*$";
    }
}
