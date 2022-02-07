package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Email;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.util.regex.Pattern;

public class EmailValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(Email email, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, email.groups(), email.condition(), klass, object, email.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateRegexp(o, regexp() , 0, email.message(), fieldName);
    }

    @Override
    protected String regexp() {
        return "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
    }
}
