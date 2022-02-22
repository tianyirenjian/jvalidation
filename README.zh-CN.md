JValidation
=======

[![License](https://img.shields.io/badge/license-apache2.0-green.svg)](https://github.com/tianyirenjian/jvalidation/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.tianyisoft.jvalidate/jvalidation.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.tianyisoft.jvalidate%22%20AND%20a:%22jvalidation%22)

中文 | [English](./README.md)

JValidation 是为 spring boot 开发的验证库。内置多种验证器, 主要是参考 Laravel 框架的验证器。目前可用的验证类正在新增中。 相比大多数验证器来说，最大的优点是支持数据库验证。

安装方法
---------------

```xml
<dependency>
  <groupId>com.tianyisoft.jvalidate</groupId>
  <artifactId>jvalidation</artifactId>
  <version>1.5.0</version>
</dependency>
```

使用说明
----------------

##### 第一种

 1. 在 SpringBootApplication 上面添加 `@EnableJValidate` 注解。
 2. 在要使用验证的 controller 的方法上加上 `@Jvalidated` 注解 (1.5.0 版本后不再需要)
 3. 在要使用验证的 controller 的方法的参数上加上 `@Jvalidated` 注解, 支持分组和设置数据源
 4. 然后就可以在要验证的类里面写各种验证规则了

如下代码:

```java
@JValidated // (1.5.0 版本后不再需要)
@PostMapping("/users")
public User store(@RequestBody @JValidated User user) {
    return user;
}
```

上面代码验证错误时会返回 422 错误，如果想自己处理错误，可以使用一个 BindingErrors 类接收到错误信息:
```java
@PostMapping("/users")
public User store(@RequestBody @JValidated(groups={xxx.class}) User user, BindingErrors bindingErrors) {
    if (bindingErrors.hasErrors()) {
        //...
    }
    return user;
}
```

##### 第二种
如果不使用注解的方式，也支持静态调用，提供了两个方法, 可以返回包含错误的 map，自行处理:

```java
com.tianyisoft.jvalidate.JValidator.validate(JdbcTemplate jdbcTemplate, Object object, Class<?>[] groups) // 使用数据库
com.tianyisoft.jvalidate.JValidator.validateWithoutJdbcTemplate(Object object, Class<?>[] groups) // 不使用数据库
```

开始验证
-------------

在要验证的 User 类添加验证规则，如:

```java

import com.tianyisoft.jvalidate.annotations.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class User {
    @Bail // name 验证第一次失败时不再继续验证 name。不影响其他字段
    @Required(message = "%s 不要为空") // 验证不为null， 字符串不等于空字符串，数组或 Collection 对象长度大于 0
    @Alpha // 只允许字母
    @Between(min = 6, max = 10) // 长度在 6 - 10 之间
    private String name;
    @Required
    @Url // 是一个合法的 url 地址
    private String homepage;
    @Required
    @Email // 是合法的 email 地址
    @Unique(table = "users", field = "email", groups = {Create.class}) // 验证在数据库不重复, 在创建时
    @Unique(table = "users", field = "email", groups = {Update.class}, where = " and id != {{ request.path.id }} ") // 验证在数据库不重复，除去 id 等于 request 的 path 参数 id, 在修改时用
    @EndsWith(ends = {"com", "cc"}) // 以 com 或 cc 结尾
    private String email;
    @AfterOrEqual(date = "1980-01-01") // 日期大于等于指定日期
    @BeforeOrEqual(date = "2013-12-31") // 日期小于等于指定日期
    private Date birthday;
    @After(date = "1980-01-01T00:00:00.000Z")  // 日期大于等于指定日期, 字段类型为 Instant
    @AfterOrEqual(date = "1980-01-01T00:00:00.000Z") // 日期小于等于指定日期, 字段类型为 Instant
    private Instant birthday2;

    @Between(min = 8, max = 70) // 年龄在 8 - 70 之间
    private Integer age;
    @Min(0) // 最小值
    @Max(100) // 最大值
    private Long score;
    @Distinct // 不允许有重复值
    @Between(min= 1, max= 2) // 长度限制
    private List<String> hobbies;

 // getters and setters
}
class Update{}
```

当验证失败会返回 422 错误，在消息体返回错误详情:

```json
{
    "message": "The given data was invalid.",
    "errors": {
        "birthday": [
            "birthday 必须是大于或等于 1980-01-01 的日期"
        ],
        "score": [
            "score 不能大于 100"
        ],
        "hobbies": [
            "hobbies 必须在 1 和 2 之间"
        ],
        "name": [
            "name 只能由字母组成"
        ],
        "email": [
            "email 在 users 中已存在"
        ],
        "age": [
            "age 必须在 8 和 70 之间"
        ]
    }
}
```

返回状态码和错误结构也可以自定义修改，只需要创建一个名为 `validateFailedExceptionHandler` 的 bean，然后就可以自己捕获 `ValidateFailedException ` 来自行处理错误了。

```java
@Bean
public void validateFailedExceptionHandler() {}
```
当参数含有 `BindingErrors` 类型时，会把错误信息放到里面，不再自动返回 422 错误。用法类似 `BindingResult`。不含有时还按之前的错误逻辑。



说明
-----------------

JValidation 使用默认的 dataSource, 可能通过 `jvalidation.datasource-name` 来使用其他数据源

根据条件决定是否要验证
-----------------

所有的验证器都可以接受一个 Condition 的实现类，使用类中的 needValidate 方法判断是否需要验证

Condition 接口的 needValidate 方法接受 Object[] 的参数，参数可以通过验证器的 params 传递， params 可以直接传递字符串，
也可以传递 {{ this }} 表示当前对象， {{ xxx }} 表示当前对象的其他字段，
或者使用 {{ request.path.id / request.get.id / request.header[s].id }} 这种方式来获取 request 中的信息

示例:

```java
import com.tianyisoft.jvalidate.annotations.RequiredIf;

public class User {
    @Required(condition = NameCondition.class, params = {"foo", "{{ this }}", "{{ bar }}"})
    private String name;
    // getters and setters
}

class NameCondition implements Condition {
    @override
    public Boolean needValidate(Object[] args) {
        System.out.println(Arrays.toString(args)); // 查看传递过来的参数
        // 根据参数判断是否要验证
        return true;
    }
}
```

支持的验证方式
-----------------

##### Accepted
必须是 "yes" ，"on" ，"1" 或 "true"

##### After
必须是在 date 的日期之后，date 可以是日期值也可以是其他的字段名，当是其他字段时，需要是相同类型

##### AfterOrEqual
必须大于或等于 date的日期。date 使用同 After

##### Alpha
必须由字母组成

##### AlphaDash
只能包含字母、数字，短破折号（-）和下划线（_）组成

##### AlphaNum
只能由字母和数字组成

##### Bail
当遇到第一个失败时，停止后续验证，只针对当前字段，其他字段还会继续验证，为了正常使用，请放到字段验证器的第一个

##### Before
与 AfterOrEqual 相反

##### BeforeOrEqual
与 After 相反

##### Between
当字段为数字时，表示值在数字中间，当字段为字符串、数组或 Collection 的子类时，表示字段长度在最大和最小值之间

##### DateEquals
必须是等于 date 的日期

##### Different
必须和指定的字段有不同的值，可以选择设置 `strict` 选择严格模式，严格模式使用 `==` 比较，否则使用 `equals` 比较

##### Distinct
只能用于 list 或数组， 要求其中不能有重复的值

##### Email
必须是 email 地址

##### EndsWith
字符串必须以指定的几个值中的一个结尾

##### Exists
必须在数据库已存在，需要数据库支持。

例如: `@Exists(table = "users", field = "email", where = " and id != {{id}} ")`

或者: `@Exists(sql = "select count(*) from users where email = ? and id = {{ request.path.user }}")`

表示 users 表里面的 email 字段必须等于当前字段值，通过 where 语句排除了 id 等于当前对象的 id 值的.

在where 条件里面可以使用 {{ request.path.id / request.get.id / request.header[s].id }} 这种方式来获取 request 中的信息，这在修改对象的时候特别有用。

##### In
验证字符串必须在给定的值中

##### Ip
必须是 ip 地址， ipv4 或者 ipv6都可以

##### Ipv4
必须是 ipv4 地址

##### Ipv6
必须是 ipv6 地址

##### Max
当字段为数字时，表示最大值，当字段为字符串、数组或 Collection 的子类时，表示最大长度

##### Min
当字段为数字时，表示最小值，当字段为字符串、数组或 Collection 的子类时，表示最小长度

##### NotRegexp
必须不符合正则表达式

##### Regexp
必须符合正则表达式

##### Required
不可以为 null, 当 allowEmpty 为 false 时，字符串不能为空，数组或 Collection 对象长度不能为 0

##### RequiredIf
已废弃，可以直接使用 Required 实现

##### StartsWith
字符串必须以指定的几个值中的一个开头

##### Unique
不能在数据库重复，需要数据库支持。

例如: `@Unique(table = "users", field = "email", where = " and id != {{id}} ")` 

或者: `@Unique(sql = "select count(*) from users where email = ? and id != {{ request.path.user }}")`
 
表示 users 表里面的 email 字段不能重复，通过 where 语句排除了 id 等于当前对象的 id 值的.

在where 条件里面可以使用 {{ request.path.id / request.get.id / request.header[s].id }} 这种方式来获取 request 中的信息，这在修改对象的时候特别有用。
 
##### UniqueGroup
用于组合多个 Unique

##### Url
字段值必须是 url 地址

更多规则添加中...

功能添加中，文档优化中...
