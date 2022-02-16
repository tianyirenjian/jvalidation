package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Required;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.util.Collection;
import java.util.regex.Pattern;

public class RequiredValidator extends Validator {
    public Tuple2<Boolean, String> validate(Required required, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        if (notNeedValidate(groups, required.groups(), required.condition(), klass, object, required.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateRequired(o, required.allowEmpty(), required.allowBlank(), required.message(), fieldName);
    }

    protected Tuple2<Boolean, String> validateRequired(Object o, Boolean allowEmpty, Boolean allowBlank, String message, String fieldName) {
        if (o == null) {
            return falseResult(message, fieldName);
        }
        if (!allowEmpty) {
            if (o instanceof String && ((String) o).length() == 0) {
                return falseResult(message, fieldName);
            }
            Class<?> clazz = o.getClass();
            if (clazz.isArray() && ((Object[]) o).length == 0) {
                return falseResult(message, fieldName);
            }
            if (o instanceof Collection && ((Collection<?>) o).size() == 0) {
                return falseResult(message, fieldName);
            }
        }
        if (!allowBlank) {
            if (o instanceof String) {
                if (Pattern.compile("^\\s+$").matcher((String) o).matches()) {
                    return falseResult(message, fieldName);
                }
            }
        }
        return trueResult();
    }
}
