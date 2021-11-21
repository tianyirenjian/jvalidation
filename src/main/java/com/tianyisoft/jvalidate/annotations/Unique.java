package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.*;

@JValidate
@NeedDatabase
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Uniques.class)
public @interface Unique {
    String message() default "%s 在 %s 中已存在";
    String table();
    String field();
    String[] excludeKeys() default {};
    String[] excludeValues() default {};
    String where() default "";
    Class<?>[] groups() default {};
}
