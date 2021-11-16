package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.BetweenList;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class BetweenListValidator extends Validator {
    public Tuple2<Boolean, String> validate(BetweenList betweenList, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return trueResult();
        }
        if (o instanceof List) {
            List<Object> listO = new ArrayList<>((List<?>) o);
            if (listO.size() >= betweenList.minLength() && listO.size() <= betweenList.maxLength()) {
                return trueResult();
            }
        }
        return falseResult(betweenList.message(), fieldName, betweenList.minLength(), betweenList.maxLength());
    }
}
