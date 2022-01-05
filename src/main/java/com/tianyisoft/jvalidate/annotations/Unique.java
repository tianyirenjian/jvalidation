package com.tianyisoft.jvalidate.annotations;

import java.lang.annotation.*;

/**
 * 用于验证数据库中不存在给定的数据，支持在一个字段上使用多个
 */
@JValidate
@NeedDatabase
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(UniqueGroup.class)
public @interface Unique {
    /**
     * 验证失败时返回的错误描述
     * @return 错误描述，自定义时可以有两个 %s 占位符，表示当前字段名和表名
     */
    String message() default "%s 在 %s 中已存在";
    /**
     * 数据库表名
     * @return 数据库表名
     */
    String table();
    /**
     * 数据库字段名
     * @return 数据库字段名
     */
    String field();
    /**
     * 附加 where 语句, 需要使用 and 或 or 开头
     * 这在修改数据做唯一验证时特别管用
     * 可以使用 {{ xx }} 来表示其他字段的值
     * 或者 {{ request.path.xx}} {{ request.get.xx }} {{ request.header[s].xx }} 来使用 request 对象中的值
     * @return 附加 where 语句
     */
    String where() default "";
    /**
     * 验证分组，通过和 Jvalidated 注解的分组做运算确定是否需要验证
     * @return 分组类的数组
     */
    Class<?>[] groups() default {};
}
