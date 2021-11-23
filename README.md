JValidation
=======

[![Maven Central](https://img.shields.io/maven-central/v/com.tianyisoft.jvalidate/jvalidation.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.tianyisoft.jvalidate%22%20AND%20a:%22jvalidation%22)

JValidation 是为 spring boot 开发的验证库。集成多种验证，目前正在新增中。

安装方法
---------------

```xml
<dependency>
  <groupId>com.tianyisoft.jvalidate</groupId>
  <artifactId>jvalidation</artifactId>
  <version>0.2.0</version>
</dependency>
```

使用说明
----------------
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

当验证失败会返回 400 错误，在消息体返回错误详情:

```json
{
    "ip": [
        "ip 必须是有效的 ip v4 地址"
    ],
    "name": [
        "name 只能由字母组成"
    ],
    "weight": [
        "weight 必须在 30.000000 和 230.000000 之间"
    ]
}
```

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
- Unique: 不能在数据库重复，需要数据库支持。例如: `@Unique(table = "users", field = "email", excludeKeys = {"id"}, excludeValues = {"39"}, where = " and id != {{id}} ")` 表示 users 表里面的 email 字段不能重复，并且排除 id = 39 的，然后通过 where 语句排除了 id 等于当前对象的 id 值的.
- Uniques: 用于组合多个 Unique
- Url: 字段值必须是 url 地址
- 更多规则添加中。。。


功能添加中，文档优化中。。。
