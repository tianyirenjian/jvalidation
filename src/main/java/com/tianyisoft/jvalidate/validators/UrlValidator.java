package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Url;
import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class UrlValidator extends Validator {
    public Tuple2<Boolean, String> validate(Url url, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), url.groups(), url.condition(), vParams.getKlass(), vParams.getObject(), url.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        if (o == null) {
            return trueResult();
        }
        if (o instanceof String) {
            String urlString = (String)o;
            try {
                URL ur = new URL(urlString);
                ur.toURI();
                return trueResult();
            } catch (MalformedURLException | URISyntaxException ignored) {
            }
        }
        return falseResult(vParams.getMessages(), url.message(), mapOf(
                Pair.of("attribute", vParams.getFieldName()),
                Pair.of("input", o)
        ));
    }
}
