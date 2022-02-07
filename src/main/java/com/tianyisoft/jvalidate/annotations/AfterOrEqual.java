package com.tianyisoft.jvalidate.annotations;

import com.tianyisoft.jvalidate.Condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于验证时间必须等于或在给定的时间之后
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterOrEqual {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有两个 %s 占位符，表示当前字段名和要比较的时间
     */
    String message() default "%s 必须是 %s 之后的日期或等于 %s";

    /**
     * @return 要比较的日期或其他字段名
     * @see After#date()
     */
    String date();

    /**
     * @return 是否包含时间
     * @see After#withTime()
     */
    boolean withTime() default false;

    /**
     * 判断是否需要进行验证的类，这个类要实现 Condition 接口，验证类会调用里面的 needValidate 方法来判断是否进行验证
     * needValidate 方法的参数可以通过 params 传递进去
     *
     * @return Condition 实现类
     */
    Class<? extends Condition> condition() default Condition.class;

    /**
     * 给 Condition 实现类传递的参数
     * 可以使用 {{ this }} 来表示当前验证的整个对象,也可以使用 {{ xx }} 来表示其他字段
     * 或者 {{ request.path.xx}} {{ request.get.xx }} {{ request.header[s].xx }} 来使用 request 对象中的值, 也可以在方法里面自己取值
     *
     * @return Condition 实现类 needValidate 就去的参数
     */
    String[] params() default {};

    /**
     * @return 分组类的数组
     * @see After#groups()
     */
    Class<?>[] groups() default {};
}
