package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JValidate
@NeedDatabase
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExistsGroup {
    Exists[] value();
    Class<?>[] groups() default {};
}
