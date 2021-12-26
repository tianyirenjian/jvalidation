package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JValidate
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Max {
    String message() default "%s 不能大于 %s";
    double value();
    Class<?>[] groups() default {};
}
