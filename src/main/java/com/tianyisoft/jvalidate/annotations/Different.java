package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段值要与另一个字段值不同
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Different {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有两个 %s 占位符，表示当前字段名和要比较的字段名
     */
    String message() default "%s 必须与 %s 有不同的值";

    /**
     * 要比较的字段名
     *
     * @return 要比较的字段名
     */
    String field();

    /**
     * 是否严格比较，严格比较用 ==，否则使用 equals
     *
     * @return 是否严格比较
     */
    boolean strict() default false;

    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     *
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
