package com.tianyisoft.jvalidate;

public interface Condition {
    default Boolean needValidate(Object[] args) {
        return true;
    }
}
