package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.DateEquals;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class DateEqualsValidator extends BaseDateValidator {
    public Tuple2<Boolean, String> validate(DateEquals dateEquals, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), dateEquals.groups(), dateEquals.condition(), vParams.getKlass(), vParams.getObject(), dateEquals.params())) {
            return trueResult();
        }
        return validateDate(vParams, dateEquals.withTime(), dateEquals.date(), dateEquals.message(), "e");
    }
}
