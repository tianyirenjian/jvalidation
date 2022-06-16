package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Distinct;
import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class DistinctValidator extends Validator {
    public Tuple2<Boolean, String> validate(Distinct distinct, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), distinct.groups(), distinct.condition(), vParams.getKlass(), vParams.getObject(), distinct.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        if (o == null || (!(o.getClass().isArray()) && !(o instanceof List))) {
            return trueResult();
        }
        if (o.getClass().isArray()) {
            Object[] oArray = (Object[]) o;
            Set<Object> set = new HashSet<>(oArray.length);
            set.addAll(Arrays.asList(oArray));
            if (set.size() == oArray.length) {
                return trueResult();
            }
        } else {
            Set<?> set = new HashSet<>((List<?>) o);
            if (set.size() == ((List<?>) o).size()) {
                return trueResult();
            }
        }
        return falseResult(vParams.getMessages(), distinct.message(), mapOf(
                Pair.of("attribute", vParams.getFieldName()),
                Pair.of("input", o)
        ));
    }
}
