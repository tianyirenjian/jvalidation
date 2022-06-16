package com.tianyisoft.jvalidate.validators;

import com.tianyisoft.jvalidate.utils.Pair;
import com.tianyisoft.jvalidate.utils.Tuple2;
import com.tianyisoft.jvalidate.utils.ValidatorParams;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import static com.tianyisoft.jvalidate.utils.Helper.mapOf;

public class BaseDateValidator extends Validator {
    protected Tuple2<Boolean, String> validateDate(
            ValidatorParams vParams,
            Boolean withTime,
            String date,
            String message,
            String operator
    ) throws NoSuchFieldException, IllegalAccessException {
        Class<?> klass = vParams.getKlass();
        Object object = vParams.getObject();
        String fieldName = vParams.getFieldName();
        Object o = getFieldValue(klass, object, fieldName);
        if (o == null) {
            return trueResult();
        }
        if (!withTime) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try { // after.date as date
                if (o instanceof Date) {
                    Date d = dateFormat.parse(date);
                    if (operatorDate((Date) o, d, operator)) {
                        return trueResult();
                    }
                }
                if (o instanceof LocalDate) {
                    LocalDate d = LocalDate.parse(date);
                    if (operatorLocalDate((LocalDate) o, d, operator)) {
                        return trueResult();
                    }
                }
                if (o instanceof Instant) {
                    Instant d = Instant.parse(date);
                    if (operatorInstant((Instant) o, d, operator)) {
                        return trueResult();
                    }
                }
            } catch (ParseException e) { // after.date as field
                Field field = getAnotherField(klass, date);
                if (field != null) {
                    Object p = getFieldValue(klass, object, field.getName());
                    if (p.getClass().equals(o.getClass())) { // 要求类型相等
                        if (o instanceof Date && operatorDate((Date) o, (Date) p, operator)) {
                            return trueResult();
                        }
                        if (o instanceof LocalDate && operatorLocalDate((LocalDate) o, (LocalDate) p, operator)) {
                            return trueResult();
                        }
                        if (o instanceof Instant && operatorInstant((Instant) o, (Instant) p, operator)) {
                            return trueResult();
                        }
                    }
                }
            }
        } else {// with time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                if (o instanceof Date) {
                    Date d = dateFormat.parse(date);
                    if (operatorDate((Date) o, d, operator)) {
                        return trueResult();
                    }
                }
                if (o instanceof Instant) {
                    Instant d = Instant.parse(date);
                    if (operatorInstant((Instant) o, d, operator)) {
                        return trueResult();
                    }
                }
            } catch (ParseException e) {
                Field field = getAnotherField(klass, date);
                if (field != null) {
                    Object p = getFieldValue(klass, object, field.getName());
                    if (p.getClass().equals(o.getClass())) { // 要求类型相等
                        if (o instanceof Date && operatorDate((Date) o, (Date) p, operator)) {
                            return trueResult();
                        }
                        if (o instanceof Instant && operatorInstant((Instant) o, (Instant) p, operator)) {
                            return trueResult();
                        }
                    }
                }
            }
        }

        return falseResult(vParams.getMessages(), message, mapOf(
                Pair.of("attribute", fieldName),
                Pair.of("input", o),
                Pair.of("date", date)
        ));
    }

    private Boolean operatorDate(Date d1, Date d2, String operator) {
        d1 = new Date(d1.getTime() - TimeZone.getDefault().getRawOffset());
        if (Objects.equals(operator, "gt")) return d1.after(d2);
        if (Objects.equals(operator, "gte")) return d1.after(d2) || d1.equals(d2);
        if (Objects.equals(operator, "e")) return d1.equals(d2);
        if (Objects.equals(operator, "lt")) return d1.before(d2);
        if (Objects.equals(operator, "lte")) return d1.before(d2) || d1.equals(d2);
        return false;
    }

    private Boolean operatorLocalDate(LocalDate d1, LocalDate d2, String operator) {
        if (Objects.equals(operator, "gt")) return d1.isAfter(d2);
        if (Objects.equals(operator, "gte")) return d1.isAfter(d2) || d1.isEqual(d2);
        if (Objects.equals(operator, "e")) return d1.isEqual(d2);
        if (Objects.equals(operator, "lt")) return d1.isBefore(d2);
        if (Objects.equals(operator, "lte")) return d1.isBefore(d2) || d1.isEqual(d2);
        return false;
    }

    private Boolean operatorInstant(Instant d1, Instant d2, String operator) {
        if (Objects.equals(operator, "gt")) return d1.isAfter(d2);
        if (Objects.equals(operator, "gte")) return d1.isAfter(d2) || d1.equals(d2);
        if (Objects.equals(operator, "e")) return d1.equals(d2);
        if (Objects.equals(operator, "lt")) return d1.isBefore(d2);
        if (Objects.equals(operator, "lte")) return d1.isBefore(d2) || d1.equals(d2);
        return false;
    }
}
