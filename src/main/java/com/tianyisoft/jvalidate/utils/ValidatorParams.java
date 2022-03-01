package com.tianyisoft.jvalidate.utils;

import org.springframework.jdbc.core.JdbcTemplate;

public class ValidatorParams {
    private final Class<?>[] groups;
    private final JdbcTemplate jdbcTemplate;
    private final Class<?> klass;
    private final Object object;
    private final String fieldName;

    public ValidatorParams(Class<?>[] groups, JdbcTemplate jdbcTemplate, Class<?> klass, Object object, String fieldName) {
        this.groups = groups;
        this.jdbcTemplate = jdbcTemplate;
        this.klass = klass;
        this.object = object;
        this.fieldName = fieldName;
    }

    public Class<?>[] getGroups() {
        return groups;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public Class<?> getKlass() {
        return klass;
    }

    public Object getObject() {
        return object;
    }

    public String getFieldName() {
        return fieldName;
    }
}
