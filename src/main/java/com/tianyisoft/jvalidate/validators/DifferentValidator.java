package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Different;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.Objects;

public class DifferentValidator extends Validator {
    public Tuple2<Boolean, String> validate(Different different, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), different.groups(), different.condition(), vParams.getKlass(), vParams.getObject(), different.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        Object o2 = getFieldValue(vParams.getKlass(), vParams.getObject(), different.field());
        if (different.strict()) {
            return o == o2 ? falseResult(different.message(), vParams.getFieldName(), different.field()) : trueResult();
        } else {
            return Objects.equals(o, o2) ?falseResult(different.message(), vParams.getFieldName(), different.field()) : trueResult();
        }
    }
}
