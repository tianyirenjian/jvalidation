package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Email;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.regex.Pattern;

public class EmailValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(Email email, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), email.groups(), email.condition(), vParams.getKlass(), vParams.getObject(), email.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        return validateRegexp(o, regexp() , 0, email.message(), vParams.getFieldName());
    }

    @Override
    protected String regexp() {
        return "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
    }
}
