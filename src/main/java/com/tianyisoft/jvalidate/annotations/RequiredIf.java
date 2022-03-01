package com.tianyisoft.jvalidate.annotations;

import com.tianyisoft.jvalidate.Condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当满足条件时， 验证字段值不能为 null
 *
 * @since 1.3.0
 */
@JValidate
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface RequiredIf {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有一个 %s 占位符，表示当前字段名
     */
    String message() default "required";

    /**
     * 是否允许为空，当不允许时，字符串不能为空，数组或 Collection 对象长度不能为 0
     *
     * @return 是否允许空
     */
    boolean allowEmpty() default false;

    /**
     * 是否允许为空字符串，只有类型是字符串时起作用
     * @return 是否允许为空字符串
     */
    boolean allowBlank() default false;

    /**
     * 判断是否需要验证 required 的类，这个类要实现 Condition 接口，验证类会调用里面的 needValidate 方法来判断是否进行验证
     * needValidate 方法的参数可以通过 params 传递进去
     *
     * @return Condition 实现类
     */
    Class<? extends Condition> condition();

    /**
     * 给 Condition 实现类传递的参数
     * 可以使用 {{ this }} 来表示当前验证的整个对象,也可以使用 {{ xx }} 来表示其他字段
     * 或者 {{ request.path.xx}} {{ request.get.xx }} {{ request.header[s].xx }} 来使用 request 对象中的值, 也可以在方法里面自己取值
     *
     * @return Condition 实现类 needValidate 就去的参数
     */
    String[] params() default {};

    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     *
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
