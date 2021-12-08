package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Different {
    String message() default "%s 必须与 %s 有不同的值";
    String field();
    boolean strict() default false;
    Class<?>[] groups() default {};
}
