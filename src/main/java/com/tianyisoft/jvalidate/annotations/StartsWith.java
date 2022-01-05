package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证字符串以给定的值开头
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StartsWith {
    /**
     * 验证失败时返回的错误描述
     * @return 错误描述，自定义时可以有两个 %s 占位符，表示当前字段名和给定的值
     */
    String message() default "%s 必须以 %s 开头";

    /**
     * 一个或多个要比较的值
     * @return 要比较的值
     */
    String[] starts();
    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
