package com.tianyisoft.jvalidate.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于组合多个 Unique 验证
 */
@JValidate
@NeedDatabase
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueGroup {
    /**
     * 多个 Unique 验证
     *
     * @return 多个 Unique 验证
     */
    Unique[] value();

    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     *
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
