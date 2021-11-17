package com.tianyisoft.jvalidate.annotations;

import com.tianyisoft.jvalidate.aops.JDoValidate;
import com.tianyisoft.jvalidate.exceptions.ValidateFailedExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({JDoValidate.class, ValidateFailedExceptionHandler.class})
public @interface EnableJValidate {
}
