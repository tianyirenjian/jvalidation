package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.UniqueGroup;
import com.tianyisoft.jvalidate.utils.Tuple2;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class UniqueGroupValidator extends UniqueValidator {
    public Tuple2<Boolean, String> validate(UniqueGroup uniqueGroup, Class<?>[] groups, JdbcTemplate jdbcTemplate, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, uniqueGroup.groups(), uniqueGroup.condition(), klass, object, uniqueGroup.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        StringBuilder builder = new StringBuilder();
        AtomicBoolean b = new AtomicBoolean(true);
        Arrays.stream(uniqueGroup.value()).forEach(unique -> {
            try {
                Tuple2<Boolean, String> result = validate(unique, groups, jdbcTemplate, klass, object, fieldName);
                if (!result.getV0()) {
                    builder.append(result.getV1());
                    builder.append(",");
                }
                b.set(b.get() && result.getV0());
            } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
        if (b.get()) {
            return trueResult();
        }
        return falseResult(builder.length() > 0 ? builder.toString().substring(0, builder.toString().length() - 1) : "");
    }
}
