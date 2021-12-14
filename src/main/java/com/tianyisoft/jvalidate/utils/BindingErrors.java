package com.tianyisoft.jvalidate.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindingErrors implements Serializable {
    public static final long serialVersionUID = 4286532481L;

    private Map<String, List<String>> errors;

    public BindingErrors() {
        this.errors = new HashMap<>();
    }

    public BindingErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public List<String> getError(String key) {
        return errors.getOrDefault(key, new ArrayList<>());
    }

    public Boolean hasErrors() {
        return errors.size() > 0;
    }

    public Boolean hasError(String key) {
        return this.getError(key).size() > 0;
    }
}
