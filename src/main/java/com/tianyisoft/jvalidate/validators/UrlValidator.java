package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Url;
import com.tianyisoft.jvalidate.utils.Tuple2;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlValidator extends Validator {
    public Tuple2<Boolean, String> validate(Url url, Class<?>[] groups, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, url.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return trueResult();
        }
        if (o instanceof String) {
            String urlString = (String)o;
            try {
                URL ur = new URL(urlString);
                ur.toURI();
                return trueResult();
            } catch (MalformedURLException | URISyntaxException e) {
                return falseResult(url.message(), fieldName);
            }
        }
        return falseResult(url.message(), fieldName);
    }
}
