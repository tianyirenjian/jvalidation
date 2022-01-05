package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可以用来验证字符串，数字，数组或 collection。
 * 当字段是字符串时，验证字符串长度不能小于给定数字，
 * 当字段是数字时，验证数字不能小于给定数字，
 * 当字段是数组时，验证数组长度不能小于给定数字，
 * 当字段是 collection 时，验证 collection size 不能小于给定数字。
 */
@JValidate
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Min {
    /**
     * 验证失败时返回的错误描述
     * @return 错误描述，自定义时可以有两个 %s 占位符，表示当前字段名和最小值
     */
    String message() default "%s 不能小于 %s";
    /**
     * 最小值
     * @return 最小值
     */
    double value();
    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
