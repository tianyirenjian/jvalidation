package com.tianyisoft.jvalidate.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JValidate
@NeedDatabase
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueGroup {
    Unique[] value();
    Class<?>[] groups() default {};
}
