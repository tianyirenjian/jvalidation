package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证发生失败时，停止当前字段接下来的其他验证。
 * 为了正常生效，要放到字段注解的第一个
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bail {
    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
