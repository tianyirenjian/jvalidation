package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JValidate
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Min {
    String message() default "%s 不能小于 %s";
    double value();
    Class<?>[] groups() default {};
}
