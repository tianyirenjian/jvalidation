package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于判断字符串必须为 yes, on, 1 或 true
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Accepted {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有一个 %s 占位符，表示当前字段名
     */
    String message() default "%s 必须是 \"yes\" ，\"on\" ，\"1\" 或 \"true\"";

    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     *
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
