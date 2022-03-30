package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Accepted;
import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.Arrays;
import java.util.List;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class AcceptedValidator extends Validator {
    public Tuple2<Boolean, String> validate(Accepted accepted, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), accepted.groups(), accepted.condition(), vParams.getKlass(), vParams.getObject(), accepted.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        List<String> acceptedValues = Arrays.asList("on", "yes", "1", "true");
        if (o == null || (o instanceof String && (acceptedValues.contains(o)))) {
            return trueResult();
        }
        return falseResult(vParams.getMessages(), accepted.message(), mapOf(
                Pair.of("attribute", vParams.getFieldName()),
                Pair.of("input", o)
        ));
    }
}
