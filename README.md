JValidation
=======

[![Maven Central](https://img.shields.io/maven-central/v/com.tianyisoft.jvalidate/jvalidation.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.tianyisoft.jvalidate%22%20AND%20a:%22jvalidation%22)

JValidation 是为 spring boot 开发的验证库。集成多种验证, 主要是参考 Laravel 框架的验证器，然后因为 java 的强类型添加一些额外的验证，目前可用的验证类正在新增中。

安装方法
---------------

```xml
<dependency>
  <groupId>com.tianyisoft.jvalidate</groupId>
  <artifactId>jvalidation</artifactId>
  <version>0.4.0</version>
</dependency>
```

使用说明
----------------

##### 第一种

 1. 在 SpringBootApplication 上面添加 `@EnableJValidate` 注解。
 2. 在要使用验证的 controller 的方法上加上 `@Jvalidated` 注解
 3. 在要使用验证的 controller 的方法的参数上加上 `@Jvalidated` 注解, 支持分组
 4. 然后就可以在要验证的类里面写各种验证规则了

如下代码中 ① ②:

```java
@JValidated ①
@PostMapping("/users")
public User store(@RequestBody @JValidated(groups={xxx.class}) ② User user) {
    return user;
}
```

##### 第二种
从 0.4.0 开始，支持静态调用，即不使用注解的方式，提供了两个方法, 可以返回包含错误的 map，自行处理:

```java
com.tianyisoft.jvalidate.JValidator.validate(JdbcTemplate jdbcTemplate, Object object, Class<?>[] groups) // 使用数据库
com.tianyisoft.jvalidate.JValidator.validateWithoutJdbcTemplate(Object object, Class<?>[] groups) // 不使用数据库
```

然后在要验证的 User 类添加验证规则，如:

```java

import com.tianyisoft.jvalidate.annotations.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class User {
    private Long id;
    @Required(message = "嫑为空")
    @Alpha
    @AlphaDash
    @AlphaNum
    @BetweenString(min = 6, max = 18)
    private String name;
    @Required
    @Url
    private String homepage;
    @Required
    @Email
    @Unique(table = "users", field = "email")
    @Unique(table = "users", field = "email", excludeKeys = {"id"}, excludeValues = {"39"}, where = " and id != {{id}} ")
    @Regexp(rule = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @After(date = "1980-01-01")
    @AfterOrEqual(date = "1980-01-01")
    @Before(date = "2003-12-31")
    @BeforeOrEqual(date = "2003-12-31")
    private Date birthday;
    @After(date = "1980-01-01")
    @AfterOrEqual(date = "1980-01-01")
    private LocalDate birthday1;
    @After(date = "1980-01-01T00:00:00.000Z")
    @AfterOrEqual(date = "1980-01-01T00:00:00.000Z")
    private Instant birthday2;

    @BetweenInteger(min = 8, max = 70)
    private Integer age;
    @Required
    @BetweenDouble(min = 30.0, max = 230.0)
    private Double weight;
    @BetweenLong(min = 0, max = 100)
    private Long score;
    @BetweenList(minLength = 1, maxLength = 2)
    private List<String> hobbies;
    @Required
    @Ip
    @Ipv4(groups = {Uppdate.class})
    @Ipv6
    private String ip;

    // getters and setters
}
class Update{}
```

当验证失败会返回 422 错误，在消息体返回错误详情:

```json
{
  "message": "The given data was invalid.",
  "errors": {
    "score": [
      "score 必须在 0 和 100 之间"
    ],
    "hobbies": [
      "hobbies 长度必须在 1 和 2 之间"
    ],
    "ip": [
      "ip 不能为空"
    ],
    "name": [
      "name 只能由字母组成",
      "name 只能包含字母、数字，短破折号（-）和下划线（_）",
      "name 只能由字母和数字组成",
      "name 长度必须在 6 和 18 之间"
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

自 0.2.2 版本开始，返回状态码和错误结构可以自定义修改，只需要创建一个名为 `validateFailedExceptionHandler` 的 bean，然后就可以自己捕获 `ValidateFailedException ` 来自行处理错误了。

```java
@Bean
public void validateFailedExceptionHandler() {}
```
自 0.3.0 开始，当参数含有 `BindingErrors` 类型时，会把错误信息放到里面，不再自动返回 422 错误。用法类似 `BindingResult`。不含有时还按之前的错误逻辑。

支持的验证方式
-----------------

- Accepted: 必须是 "yes" ，"on" ，"1" 或 "true"
- After: 必须是在 date 的日期之后，date 可以是日期值也可以是其他的字段名，当是其他字段时，需要是相同类型
- AfterOrEqual: 必须大于或等于 date的日期。date 使用同 After
- Alpha: 必须由字母组成
- AlphaDash: 只能包含字母、数字，短破折号（-）和下划线（_）组成
- AlphaNum: 只能由字母和数字组成
- Before: 与 AfterOrEqual 相反
- BeforeOrEqual: 与 After 相反
- BetweenDouble: 在两个浮点数之间
- BetweenInteger : 在两个整形数之间
- BetweenList: 列表长度在 mix 和 max 之间
- BetweenLong : 在两个长整形数之间
- BetweenString : 字符串长度在 min 和 max 之间
- DateEquals: 必须是等于 date 的日期
- Email: 必须是 email 地址
- Ip: 必须是 ip 地址， ipv4 或者 ipv6都可以
- Ipv4: 必须是 ipv4 地址
- Ipv6: 必须是 ipv6 地址
- Regexp: 必须符合正则表达式
- Required: 不可以为 null
- Unique: 不能在数据库重复，需要数据库支持。
 例如: `@Unique(table = "users", field = "email", excludeKeys = {"id"}, excludeValues = {"39"}, where = " and id != {{id}} ")` 表示 users 表里面的 email 字段不能重复，并且排除 id = 39 的，然后通过 where 语句排除了 id 等于当前对象的 id 值的.
 在where 条件里面可以使用 {{ request.path.id / request.get.id / request.header[s].id }} 这种方式来获取 request 中的信息，这在修改对象的时候特别有用。
 
- Uniques: 用于组合多个 Unique
- Url: 字段值必须是 url 地址
- Different: 必须和指定的字段有不同的值，可以选择设置 `strict` 选择严格模式，严格模式使用 `==` 比较，否则使用 `equals` 比较
- Distinct: 只能用于 list， 要求 list 中不能有重复的值
- EndsWith: 字符串必须以指定的几个值中的一个结尾
- 更多规则添加中。。。


功能添加中，文档优化中。。。
