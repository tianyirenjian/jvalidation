package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证字符串必须匹配指定的正则表达式
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Regexp {
    /**
     * 验证失败时返回的错误描述
     * @return 错误描述，自定义时可以有一个 %s 占位符，表示当前字段名
     */
    String message() default "%s 正则不匹配";

    /**
     * 正则表达式
     * @return 正则表达式
     */
    String rule();

    /**
     * 正则表达式匹配模式
     * @return 正则表达式匹配模式
     */
    int flags() default 0;
    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
