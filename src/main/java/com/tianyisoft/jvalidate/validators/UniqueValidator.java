package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Unique;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UniqueValidator extends Validator {
    public Tuple2<Boolean, String> validate(Unique unique, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), unique.groups(), unique.condition(), vParams.getKlass(), vParams.getObject(), unique.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        if (o == null) {
            return trueResult();
        }
        List<Object> parameters = new ArrayList<>();
        parameters.add(o);

        String sql;
        if (Objects.equals(unique.sql(), "")) {
            Tuple2<String, List<Object>> where = explainWhere(vParams.getKlass(), vParams.getObject(), unique.where());
            parameters.addAll(where.getV1());
            sql = "select count(*) from " + unique.table() + " where `" + unique.field() + "` = ? " + where.getV0();
        } else {
            Tuple2<String, List<Object>> query = explainWhere(vParams.getKlass(), vParams.getObject(), unique.sql());
            parameters.addAll(query.getV1());
            sql = query.getV0();
        }

        Long count = vParams.getJdbcTemplate().queryForObject(sql, Long.class, parameters.toArray());
        if (count != null && count == 0L) {
            return trueResult();
        }
        return falseResult(unique.message(), vParams.getFieldName(), unique.table());
    }
}
