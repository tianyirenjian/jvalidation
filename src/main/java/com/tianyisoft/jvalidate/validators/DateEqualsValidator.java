package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.DateEquals;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class DateEqualsValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(DateEquals dateEquals, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, dateEquals.groups())) {
            return trueResult();
        }
        return validateDate(klass, object, fieldName, dateEquals.withTime(), dateEquals.date(), dateEquals.message(), "e");
    }
}
