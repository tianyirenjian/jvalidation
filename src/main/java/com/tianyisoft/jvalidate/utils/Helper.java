package com.tianyisoft.jvalidate.utils;

import com.tianyisoft.jvalidate.JValidator;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public static String readResourceFile(String file, Charset charset) throws IOException {
        InputStream inputStream = JValidator.class.getResourceAsStream(file);
        return StreamUtils.copyToString(inputStream, charset);
    }

    @SafeVarargs
    public static <T, K> Map<T, K> mapOf(Pair<T, K>... pairs) {
        return Arrays.stream(pairs).reduce(new HashMap<T, K>(), (map, pair) -> {
            map.put(pair.first(), pair.second());
            return map;
        }, (map, pair) -> {return map;});
    }
}
