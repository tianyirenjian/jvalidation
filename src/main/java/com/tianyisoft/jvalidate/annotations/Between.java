package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Between {
    String message() default "%s 必须在 %s 和 %s 之间";
    double min();
    double max();
    Class<?>[] groups() default {};
}
