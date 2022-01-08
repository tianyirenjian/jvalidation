package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于验证时间必须等于或在给定的时间之前
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeOrEqual {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有两个 %s 占位符，表示当前字段名和要比较的时间
     */
    String message() default "%s 必须是 %s 之前的日期或等于 %s";

    /**
     * @return 要比较的日期或其他字段名
     * @see After#date()
     */
    String date();

    /**
     * @return 是否包含时间
     * @see After#withTime()
     */
    boolean withTime() default false;

    /**
     * @return 分组类的数组
     * @see After#groups()
     */
    Class<?>[] groups() default {};
}
