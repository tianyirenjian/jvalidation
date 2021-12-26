package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.*;

@JValidate
@NeedDatabase
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(ExistsGroup.class)
public @interface Exists {
    String message() default "%s 必须在表 %s 中已存在";
    String table();
    String field();
    String where() default "";
    Class<?>[] groups() default {};
}
