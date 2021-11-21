package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Unique;
import com.tianyisoft.jvalidate.utils.Tuple2;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueValidator extends Validator {
    public Tuple2<Boolean, String> validate(Unique unique, Class<?>[] groups, JdbcTemplate jdbcTemplate, Class<?> klass, Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (!needValidateByGroups(groups, unique.groups())) {
            return trueResult();
        }
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return trueResult();
        }
        List<Object> parameters = new ArrayList<>();
        parameters.add(o);
        Tuple2<String, List<Object>> where = explainWhere(klass, object, unique.where());
        parameters.addAll(where.getV1());
        StringBuilder sql = new StringBuilder("select count(*) as aggregate from " + unique.table() + " where `" + unique.field() + "` = ? " + where.getV0());
        if (unique.excludeKeys().length != 0 && unique.excludeKeys().length == unique.excludeValues().length) {
            for (int i = 0; i < unique.excludeKeys().length; i ++) {
                sql.append(" and `").append(unique.excludeKeys()[i]).append("` != ?");
                parameters.add(unique.excludeValues()[i]);
            }
        }

        Long count = jdbcTemplate.queryForObject(sql.toString(), Long.class, parameters.toArray());
        if (count != null && count == 0L) {
            return trueResult();
        }
        return falseResult(unique.message(), fieldName, unique.table());
    }

    private Tuple2<String, List<Object>> explainWhere(Class<?> klass, Object object, String where) throws NoSuchFieldException, IllegalAccessException {
        Pattern pattern = Pattern.compile("\\{\\{\\s*([a-zA-Z][a-zA-Z0-9.]*)\\s*}}");
        Matcher matcher = pattern.matcher(where);
        List<Object> parameters = new ArrayList<>();
        while (matcher.find()) {
            where = where.replaceFirst(pattern.pattern(), "?");
            String field = matcher.group(1);
            parameters.add(getFieldValue(klass, object, field));
        }
        where = where.replace("\\{\\{", "{{");
        return new Tuple2<>(where, parameters);
    }
}
