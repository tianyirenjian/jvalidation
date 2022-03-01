package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.In;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.Arrays;
import java.util.Objects;

public class InValidator extends Validator{
    public Tuple2<Boolean, String> validate(In in, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), in.groups(), in.condition(), vParams.getKlass(), vParams.getObject(), in.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        if (!(o instanceof String)) {
            return trueResult();
        }
        for (String value : in.values()) {
            if (Objects.equals(value, o)) {
                return trueResult();
            }
        }
        return falseResult(in.message(), vParams.getFieldName(), Arrays.toString(in.values()));
    }
}
