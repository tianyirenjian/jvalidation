JValidation
=======

[![License](https://img.shields.io/badge/license-apache2.0-green.svg)](https://github.com/tianyirenjian/jvalidation/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.tianyisoft.jvalidate/jvalidation.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.tianyisoft.jvalidate%22%20AND%20a:%22jvalidation%22)

English | [中文版](./README.zh-CN.md)

JValidation is a validation library developed for spring boot. There are a variety of built-in validators, mainly referring to the validators of the Laravel framework. Currently available validation classes are being added. Compared to most validators, the biggest advantage is that it supports database validation.

installation
---------------

````xml
<dependency>
  <groupId>com.tianyisoft.jvalidate</groupId>
  <artifactId>jvalidation</artifactId>
  <version>1.5.0</version>
</dependency>
````

How to use
----------------

##### The first way

1. Add the `@EnableJValidate` annotation to the SpringBootApplication.
2. Add the `@Jvalidated` annotation to the method of the controller to be validated (no longer required after version 1.5.0)
3. Add the `@Jvalidated` annotation to the parameters of the method of the controller to be validated to support grouping and setting data sources
4. Then you can write various validation rules in the class to be validated

The following code:

````java
@JValidated // (no longer required after version 1.5.0)
@PostMapping("/users")
public User store(@RequestBody @JValidated User user) {
    return user;
}
````

The above code will return a 422 error when validating an error. If you want to handle the error yourself, you can use a BindingErrors class to receive the error message:
````java
@PostMapping("/users")
public User store(@RequestBody @JValidated(groups={xxx.class}) User user, BindingErrors bindingErrors) {
    if (bindingErrors.hasErrors()) {
        //...
    }
    return user;
}
````

##### Second way
If you do not use annotations, static calls are also supported. Two methods are provided, which can return a map containing errors and handle them yourself:

````java
com.tianyisoft.jvalidate.JValidator.validate(Object object, Class<?>[] groups)
com.tianyisoft.jvalidate.JValidator.validate(Object object, Class<?>[] groups, JdbcTemplate jdbcTemplate) // use database
com.tianyisoft.jvalidate.JValidator.validate(Object object, Class<?>[] groups, JdbcTemplate jdbcTemplate, String language, String defaultLang) // use i18n
````

Start validate
-------------

Add validation rules to the User class to be validated, such as:

````java

import com.tianyisoft.jvalidate.annotations.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class User {
    @Bail // Do not continue to validate name when name validation fails for the first time. Does not affect other fields
    @Required(message = "%s should not be empty") // Verify that it is not null, the string is not equal to the empty string, the length of the array or Collection object is greater than 0
    @Alpha // only letters are allowed
    @Between(min = 6, max = 10) // length is between 6 - 10
    private String name;
    @Required
    @Url // is a valid url address
    private String homepage;
    @Required
    @Email // is a valid email address
    @Unique(table = "users", field = "email", groups = {Create.class}) // Verify that the database is not duplicated, at creation time
    @Unique(table = "users", field = "email", groups = {Update.class}, where = " and id != {{ request.path.id }} ") // Verify that the database is not duplicated, remove id is equal to the id of the path parameter of the request, which is used when modifying
    @EndsWith(ends = {"com", "cc"}) // ends with com or cc
    private String email;
    @AfterOrEqual(date = "1980-01-01") // The date is greater than or equal to the specified date
    @BeforeOrEqual(date = "2013-12-31") // The date is less than or equal to the specified date
    private Date birthday;
    @After(date = "1980-01-01T00:00:00.000Z") // The date is greater than or equal to the specified date, the field type is Instant
    @AfterOrEqual(date = "1980-01-01T00:00:00.000Z") // The date is less than or equal to the specified date, the field type is Instant
    private Instant birthday2;

    @Between(min = 8, max = 70) // age is between 8 - 70
    private Integer age;
    @Min(0) // minimum value
    @Max(100) // maximum value
    private Long score;
    @Distinct // no duplicate values allowed
    @Between(min= 1, max= 2) // length limit
    private List<String> hobbies;

 // getters and setters
}
class Update{}
````

When validation fails, a 422 error will be returned, and the error details will be returned in the message body:

````json
{
    "message": "The given data was invalid.",
    "errors": {
        "birthday": [
            "birthday must be a date greater than or equal to 1980-01-01"
        ],
        "score": [
            "score cannot be greater than 100"
        ],
        "hobbies": [
            "hobbies must be between 1 and 2"
        ],
        "name": [
            "name can only consist of letters"
        ],
        "email": [
            "email already exists in users"
        ],
        "age": [
            "age must be between 8 and 70"
        ]
    }
}
````

The return status code and error structure can also be customized, just create a bean named `validateFailedExceptionHandler`, and then you can catch `ValidateFailedException` to handle errors yourself.

````java
@Bean
public void validateFailedExceptionHandler() {}
````
When the parameter contains `BindingErrors` type, the error information will be put in it, and the 422 error will no longer be automatically returned. Usage is similar to `BindingResult`. Does not sometimes follow the wrong logic before.



illustrate
-----------------

JValidation uses the default dataSource, other data sources may be used via `jvalidation.datasource-name`

Decide whether to verify based on conditions
-----------------

All validators can accept a Condition implementation class, use the needValidate method in the class to determine whether validation is required

The needValidate method of the Condition interface accepts Object[] parameters, which can be passed through the validator's params, and params can directly pass strings.
You can also pass {{ this }} to represent the current object, {{ xxx }} to represent other fields of the current object,
Or use {{ request.path.id / request.get.id / request.header[s].id }} to get the information in the request

Example:

````java
import com.tianyisoft.jvalidate.annotations.Required;

public class User {
    @Required(condition = NameCondition.class, params = {"foo", "{{ this }}", "{{ bar }}"})
    private String name;
    // getters and setters
}

class NameCondition implements Condition {
    @override
    public Boolean needValidate(Object[] args) {
        System.out.println(Arrays.toString(args)); // View the passed parameters
        // Determine whether to verify according to the parameters
        return true;
    }
}
````

Available Validation Rules
-----------------

##### Accepted
Must be "yes" , "on" , "1" or "true"

##### After
Must be after the date of date, date can be a date value or other field names, when it is other fields, it needs to be of the same type

##### AfterOrEqual
Must be a date greater than or equal to date. date is used the same as After

##### Alpha
must consist of letters

##### AlphaDash
Can only contain letters, numbers, dashes (-) and underscores (_)

##### AlphaNum
Can only consist of letters and numbers

##### Bail
When the first failure is encountered, the subsequent verification is stopped, only for the current field, and other fields will continue to be verified. For normal use, please put it in the first field of the field validator.

##### Before
Opposite of AfterOrEqual

##### BeforeOrEqual
Opposite of After

##### Between
When the field is a number, it means that the value is in the middle of the number, and when the field is a subclass of string, array or Collection, it means that the length of the field is between the maximum and minimum values

##### DateEquals
Must be a date equal to date

##### Different
Must have a different value from the specified field, you can choose to set `strict` to select strict mode, use `==` for strict mode comparison, otherwise use `equals` for comparison

##### Distinct
Can only be used for lists or arrays, where no duplicate values are required

##### Email
Must be an email address

##### EndsWith
The string must end with one of the specified values

##### Exists
The database must already exist, and database support is required.

For example: `@Exists(table = "users", field = "email", where = " and id != {{id}} ")`

Or: `@Exists(sql = "select count(*) from users where email = ? and id = {{ request.path.user }}")`

Indicates that the email field in the users table must be equal to the current field value, and the where statement excludes those whose id is equal to the current object's id value.

In the where condition, you can use {{ request.path.id / request.get.id / request.header[s].id }} to get the information in the request, which is especially useful when modifying objects.

##### In
Validation string must be in the given value

##### Ip
Must be an ip address, either ipv4 or ipv6 is fine

##### Ipv4
Must be an ipv4 address

##### Ipv6
Must be an ipv6 address

##### Max
When the field is a number, it represents the maximum value, and when the field is a subclass of String, Array or Collection, it represents the maximum length

##### Min
When the field is a number, it means the minimum value, and when the field is a subclass of string, array or Collection, it means the minimum length

##### NotRegexp
must not match the regular expression

##### Regexp
Must match regular expression

##### Required
Cannot be null, when allowEmpty is false, the string cannot be empty, and the length of the array or Collection object cannot be 0

##### RequiredIf
Obsolete, can be implemented directly using Required

##### StartsWith
String must start with one of several values specified

##### Unique
It cannot be repeated in the database and requires database support.

Example: `@Unique(table = "users", field = "email", where = " and id != {{id}} ")`

Or: `@Unique(sql = "select count(*) from users where email = ? and id != {{ request.path.user }}")`

Indicates that the email field in the users table cannot be repeated, and the id equal to the id value of the current object is excluded through the where statement.

In the where condition, you can use {{ request.path.id / request.get.id / request.header[s].id }} to get the information in the request, which is especially useful when modifying objects.

##### UniqueGroup
Used to combine multiple Unique

##### Url
Field value must be a url address

More rules are being added...

Functions are being added, documents are being optimized...