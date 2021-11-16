package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.utils.Tuple2;

public class BaseBetweenNumberValidator extends Validator {
    protected Tuple2<Boolean, String> validateNumber(Object o, Class<?> type, Object min, Object max, String message, String fieldName) {
        if (o == null) {
            return trueResult();
        }
        if (type == Integer.class) {
            if ((Integer)o >= (Integer)min && (Integer)o < (Integer) max) {
                return trueResult();
            }
        }
        if (type == Double.class) {
            if ((Double)o >= (Double)min && (Double)o < (Double) max) {
                return trueResult();
            }
        }
        if (type == Long.class) {
            if ((Long)o >= (Long)min && (Long)o < (Long) max) {
                return trueResult();
            }
        }
        return falseResult(message, fieldName, min, max);
    }
}
