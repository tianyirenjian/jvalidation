package com.tianyisoft.jvalidate.annotations;

import com.tianyisoft.jvalidate.Condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可以用来验证字符串，数字，数组或 collection。
 * 当字段是字符串时，验证字符串长度不能大于给定数字，
 * 当字段是数字时，验证数字不能大于给定数字，
 * 当字段是数组时，验证数组长度不能大于给定数字，
 * 当字段是 collection 时，验证 collection size 不能大于给定数字。
 */
@JValidate
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Max {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有两个 %s 占位符，表示当前字段名和最大值
     */
    String message() default "max";

    /**
     * 最大值
     *
     * @return 最大值
     */
    double value();

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
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     *
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
