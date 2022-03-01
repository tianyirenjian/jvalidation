package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.Exists;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExistsValidator extends Validator {
    public Tuple2<Boolean, String> validate(Exists exists, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), exists.groups(), exists.condition(), vParams.getKlass(), vParams.getObject(), exists.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        if (o == null) {
            return trueResult();
        }
        List<Object> parameters = new ArrayList<>();
        parameters.add(o);

        String sql;
        if (Objects.equals(exists.sql(), "")) {
            Tuple2<String, List<Object>> where = explainWhere(vParams.getKlass(), vParams.getObject(), exists.where());
            parameters.addAll(where.getV1());
            sql = "select count(*) as aggregate from " + exists.table() + " where `" + exists.field() + "` = ? " + where.getV0();
        } else {
            Tuple2<String, List<Object>> query = explainWhere(vParams.getKlass(), vParams.getObject(), exists.sql());
            parameters.addAll(query.getV1());
            sql = query.getV0();
        }

        Long count = vParams.getJdbcTemplate().queryForObject(sql, Long.class, parameters.toArray());
        if (count != null && count > 0) {
            return trueResult();
        }
        return falseResult(exists.message(), vParams.getFieldName(), exists.table());
    }
}
