package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Ipv4;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

public class Ipv4Validator extends IpValidator {
    public Tuple2<Boolean, String> validate(Ipv4 ipv4, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), ipv4.groups(), ipv4.condition(), vParams.getKlass(), vParams.getObject(), ipv4.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        return validateRegexp(o, regexp(), 0, ipv4.message(), vParams);
    }

    @Override
    protected String regexp() {
        return ipv4();
    }
}
