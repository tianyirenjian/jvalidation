package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用在方法上指示要验证的方法，同时用在要验证的参数上，对方法的参数进行验证
 * 在方法的参数上使用时，可以指定验证的组
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JValidated {
    /**
     * 验证分组，通过和具体验证器的分组做运算确定是否需要验证
     *
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};

    /**
     * 指定数据源名称，不指定则使用默认数据源
     * 如果特别指定为系统默认数据源，一般名字为 dataSource，默认情况不需要指定
     *
     * @return 数据源的 bean 名称
     */
    String datasourceName() default "";
}
