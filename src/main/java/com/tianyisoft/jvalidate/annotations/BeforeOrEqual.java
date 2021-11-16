package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeOrEqual {
    String message() default "%s 必须是 %s 之前的日期或等于 %s";
    String date();
    boolean withTime() default false;
}
