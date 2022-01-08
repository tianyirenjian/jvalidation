package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可以用来验证字符串，数字，数组或 collection。
 * 当字段是字符串时，验证字符串长度在给定数字之间，
 * 当字段是数字时，验证数字在给定数字之间，
 * 当字段是数组时，验证数组长度在给定数字之间，
 * 当字段是 collection 时，验证 collection size 在给定数字之间。
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Between {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有三个 %s 占位符，表示当前字段名、最小值和最大值
     */
    String message() default "%s 必须在 %s 和 %s 之间";

    /**
     * 最小值
     *
     * @return 最小值
     */
    double min();

    /**
     * 最大值
     *
     * @return 最大值
     */
    double max();

    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     *
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
