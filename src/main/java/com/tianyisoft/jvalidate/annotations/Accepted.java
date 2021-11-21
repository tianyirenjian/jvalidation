package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Accepted {
    String message() default "%s 必须是 \"yes\" ，\"on\" ，\"1\" 或 \"true\"";
    Class<?>[] groups() default {};
}
