package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证字符串只能包含字母、数字，短破折号（-）和下划线（_）
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlphaDash {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有一个 %s 占位符，表示当前字段名
     */
    String message() default "%s 只能包含字母、数字，短破折号（-）和下划线（_）";

    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     *
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
