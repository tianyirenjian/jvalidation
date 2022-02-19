package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Unique;
import com.tianyisoft.jvalidate.utils.Tuple2;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UniqueValidator extends Validator {
    public Tuple2<Boolean, String> validate(Unique unique, Class<?>[] groups, JdbcTemplate jdbcTemplate, Class<?> klass, Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(groups, unique.groups(), unique.condition(), klass, object, unique.params())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return trueResult();
        }
        List<Object> parameters = new ArrayList<>();
        parameters.add(o);

        String sql;
        if (Objects.equals(unique.sql(), "")) {
            Tuple2<String, List<Object>> where = explainWhere(klass, object, unique.where());
            parameters.addAll(where.getV1());
            sql = "select count(*) from " + unique.table() + " where `" + unique.field() + "` = ? " + where.getV0();
        } else {
            Tuple2<String, List<Object>> query = explainWhere(klass, object, unique.sql());
            parameters.addAll(query.getV1());
            sql = query.getV0();
        }

        Long count = jdbcTemplate.queryForObject(sql, Long.class, parameters.toArray());
        if (count != null && count == 0L) {
            return trueResult();
        }
        return falseResult(unique.message(), fieldName, unique.table());
    }
}
