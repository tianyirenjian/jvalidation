package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateEquals {
    String message() default "%s 必须是等于 %s 的日期";
    String date();
    boolean withTime() default false;
    Class<?>[] groups() default {};
}
