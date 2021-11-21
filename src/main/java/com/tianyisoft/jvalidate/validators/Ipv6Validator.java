package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Ipv6;
import com.tianyisoft.jvalidate.utils.Tuple2;

public class Ipv6Validator extends IpValidator {
    public Tuple2<Boolean, String> validate(Ipv6 ipv6, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, ipv6.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        return validateRegexp(o, regexp(), 0, ipv6.message(), fieldName);
    }

    @Override
    protected String regexp() {
        return ipv6();
    }
}
