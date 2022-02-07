package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.DateEquals;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class DateEqualsValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(DateEquals dateEquals, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, dateEquals.groups(), dateEquals.condition(), klass, object, dateEquals.params())) {
            return trueResult();
        }
        return validateDate(klass, object, fieldName, dateEquals.withTime(), dateEquals.date(), dateEquals.message(), "e");
    }
}
