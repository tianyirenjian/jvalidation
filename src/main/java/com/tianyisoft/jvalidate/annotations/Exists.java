package com.tianyisoft.jvalidate.annotations;

import com.tianyisoft.jvalidate.Condition;

import java.lang.annotation.*;

/**
 * 用于验证数据库中存在给定的数据，支持在一个字段上使用多个
 */
@JValidate
@NeedDatabase
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(ExistsGroup.class)
public @interface Exists {
    /**
     * 验证失败时返回的错误描述
     *
     * @return 错误描述，自定义时可以有两个 %s 占位符，表示当前字段名和表名
     */
    String message() default "%s 必须在表 %s 中已存在";

    /**
     * 数据库表名
     *
     * @return 数据库表名
     */
    String table() default "";

    /**
     * 数据库字段名
     *
     * @return 数据库字段名
     */
    String field() default "";

    /**
     * 附加 where 语句, 需要使用 and 或 or 开头
     * 可以使用 {{ xx }} 来表示其他字段的值
     * 或者 {{ request.path.xx}} {{ request.get.xx }} {{ request.header[s].xx }} 来使用 request 对象中的值
     *
     * @return 附加 where 语句
     */
    String where() default "";

    /**
     * 查询使用的 sql 语句，不为空时将忽略 table、field 和 where 的值
     * sql 语句必须是 count 语句，当 count 结果大于 0 时为存在
     * sql 语句最少有一个参数，表示当前字段的值，且当前字段的值必须是第一个，用 ？ 表示，举例:
     *
     * <example>
     *     select count(*) from user where email = ?
     * </example>
     *
     * 可以使用 {{ xx }} 来表示其他字段的值
     * 或者 {{ request.path.xx}} {{ request.get.xx }} {{ request.header[s].xx }} 来使用 request 对象中的值，举例:
     *
     * <example>
     *     select count(*) from user where email = ? and id != {{ request.path.user }}
     * </example>
     *
     * @return 查询重复需要使用的 sql 语句
     */
    String sql() default "";

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
