package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.annotations.UniqueGroup;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class UniqueGroupValidator extends UniqueValidator {
    public Tuple2<Boolean, String> validate(UniqueGroup uniqueGroup, ValidatorParams vParams)
            throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        if (notNeedValidate(vParams.getGroups(), uniqueGroup.groups(), uniqueGroup.condition(), vParams.getKlass(), vParams.getObject(), uniqueGroup.params())) {
            return trueResult();
        }
        Object o = getFieldValue(vParams.getKlass(), vParams.getObject(), vParams.getFieldName());
        StringBuilder builder = new StringBuilder();
        AtomicBoolean b = new AtomicBoolean(true);
        Arrays.stream(uniqueGroup.value()).forEach(unique -> {
            try {
                Tuple2<Boolean, String> result = validate(unique, vParams);
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
