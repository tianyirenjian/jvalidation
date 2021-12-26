package com.tianyisoft.jvalidate.validators;

import java.util.Collection;

public class BaseLengthValidator extends Validator {
    protected boolean validateLength(String operator, Object o, Double min, Double max) {
        Class<?> clazz = o.getClass();
        switch (operator) {
            case "between":
                if (clazz.isArray()) {
                    Object[] oArray = (Object[]) o;
                    return oArray.length >= min && oArray.length <= max;
                } else if (o instanceof Collection) {
                    return ((Collection<?>) o).size() >= min && ((Collection<?>) o).size() <= max;
                } else if (o instanceof String) {
                    return ((String) o).length() >= min && ((String) o).length() <= max;
                } else if (o instanceof Number) {
                    return ((Number) o).doubleValue() >= min && ((Number) o).doubleValue() <= max;
                }
            case "min":
                if (clazz.isArray()) {
                    Object[] oArray = (Object[]) o;
                    return oArray.length >= min;
                } else if (o instanceof Collection) {
                    return ((Collection<?>) o).size() >= min;
                } else if (o instanceof String) {
                    return ((String) o).length() >= min;
                } else if (o instanceof Number) {
                    return ((Number) o).doubleValue() >= min;
                }
            case "max":
                if (clazz.isArray()) {
                    Object[] oArray = (Object[]) o;
                    return oArray.length <= max;
                } else if (o instanceof Collection) {
                    return ((Collection<?>) o).size() <= max;
                } else if (o instanceof String) {
                    return ((String) o).length() <= max;
                } else if (o instanceof Number) {
                    return ((Number) o).doubleValue() <= max;
                }
        }
        return false;
    }
}
