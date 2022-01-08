package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于验证时间必须在给定的时间之后
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface After {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有两个 %s 占位符，表示当前字段名和要比较的时间
     */
    String message() default "%s 必须是 %s 之后的日期";

    /**
     * 要比较的日期，或者是另外一个字段的名称，当是另一个字段时，两个字段类型要一致
     *
     * @return 要比较的日期或其他字段名
     */
    String date();

    /**
     * 是否包含时间，决定格式化时使用的格式规则
     *
     * @return 是否包含时间
     */
    boolean withTime() default false;

    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     *
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
