package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证字段值不能为 null
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有一个 %s 占位符，表示当前字段名
     */
    String message() default "%s 不能为空";

    /**
     * 是否允许为空，当不允许时，字符串不能为空，数组或 Collection 对象长度不能为 0
     *
     * @return 是否允许空
     * @since 1.3.0
     */
    boolean allowEmpty() default false;

    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     *
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
