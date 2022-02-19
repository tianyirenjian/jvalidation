package com.tianyisoft.jvalidate.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Helper {
    public static Charset getCharset(String encoding) {
        if (encoding == null || "".equals(encoding)) {
            return StandardCharsets.UTF_8;
        }
        return Charset.forName(encoding);
    }

    public static Boolean isEmptyOrNull(String value) {
        return value == null || "".equals(value);
    }
}
