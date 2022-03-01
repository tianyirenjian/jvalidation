package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Ipv6;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class Ipv6Validator extends IpValidator {
    public Tuple2<Boolean, String> validate(Ipv6 ipv6, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), ipv6.groups(), ipv6.condition(), vParams.getKlass(), vParams.getObject(), ipv6.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        return validateRegexp(o, regexp(), 0, ipv6.message(), vParams.getFieldName());
    }

    @Override
    protected String regexp() {
        return ipv6();
    }
}
