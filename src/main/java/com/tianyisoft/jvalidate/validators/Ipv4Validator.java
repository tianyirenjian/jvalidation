package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Ipv4;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class Ipv4Validator extends IpValidator {
    public Tuple2<Boolean, String> validate(Ipv4 ipv4, Class<?>[] groups, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, ipv4.groups(), ipv4.condition(), klass, object, ipv4.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateRegexp(o, regexp(), 0, ipv4.message(), fieldName);
    }

    @Override
    protected String regexp() {
        return ipv4();
    }
}
