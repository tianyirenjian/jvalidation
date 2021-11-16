package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Unique;
import com.tianyisoft.jvalidate.utils.Tuple2;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UniqueValidator extends Validator {
    public Tuple2<Boolean, String> validate(Unique unique, JdbcTemplate jdbcTemplate, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return trueResult();
        }
        List<Object> parameters = new ArrayList<>();
        parameters.add(o);
        String sql = "select count(*) as aggregate from " + unique.table() + " where " + unique.field() + " = ? " + unique.where();
        if (!Objects.equals(unique.excludeValue(), "")) {
            sql += " and " + unique.excludeKey() + " != ?";
            parameters.add(unique.excludeValue());
        }

        Long count = jdbcTemplate.queryForObject(sql, Long.class, parameters.toArray());
        if (count != null && count == 0L) {
            return trueResult();
        }
        return falseResult(unique.message(), fieldName, unique.table());
    }
}
