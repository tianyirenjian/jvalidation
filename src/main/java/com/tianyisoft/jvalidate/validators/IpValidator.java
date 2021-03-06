package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Ip;
import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.stream.Stream;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class IpValidator extends RegexpValidator {
    public Tuple2<Boolean, String> validate(Ip ip, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), ip.groups(), ip.condition(), vParams.getKlass(), vParams.getObject(), ip.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        if (o == null) {
            return trueResult();
        }
        if (o instanceof String) {
            String value = (String) o;
            boolean b = Stream.of(ipv4(), ipv6()).anyMatch(regexp -> {
                return regexpValidate(value, regexp, 0);
            });
            if (b) return trueResult();
        }
        return falseResult(vParams.getMessages(), ip.message(), mapOf(
                Pair.of("attribute", vParams.getFieldName()),
                Pair.of("input", o)
        ));
    }

    protected String ipv4() {
        return "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)(\\.(?!$)|$)){4}$";
    }

    protected String ipv6() {
        return "^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$";
    }
}
