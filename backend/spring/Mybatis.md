<!-- TOC -->
* [A Simple Mybatis Example](#a-simple-mybatis-example)
  * [@Mapper](#mapper)
* [show sql in log](#show-sql-in-log)
* [useGeneratedKeys & keyProperty](#usegeneratedkeys--keyproperty)
* [camel case to underscore](#camel-case-to-underscore)
* [`#{}` and `${}` in mybatis config sql](#-and--in-mybatis-config-sql)
* [@Param](#param)
* [returnType](#returntype)
  * [return List](#return-list)
  * [return Map](#return-map)
* [resultMap](#resultmap)
  * [association](#association)
  * [collection](#collection)
<!-- TOC -->

# A Simple Mybatis Example

- add depencency

```xml

<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter-test</artifactId>
    <version>3.0.4</version>
    <scope>test</scope>
</dependency>
```

- configure datasource as spring boot jdbc

```properties
spring.datasource.url=jdbc:mysql://localhost:13306/ssm3?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## @Mapper

- create mapper

```java
package com.li.hellospringmybatis.mapper;

import com.li.hellospringmybatis.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {
    Emp getEmpById(Integer id);

    void addEmp(Emp emp);

    List<Emp> getEmps();

    void deleteEmpById(Integer id);

    void updateEmp(Emp emp);
}


```

- create Mybatis xml configuration file for mapper
- !!! if name in sql not the same as bean, use alias in sql select or [resultMap](#resultMap)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.li.hellospringmybatis.mapper.EmpMapper">
    <insert id="addEmp">
        insert into `emp` (`name`, `age`, `salary`)
        values (#{name}, #{age}, #{salary})
    </insert>
    <delete id="deleteEmpById">
        delete
        from `emp`
        where id = #{id}
    </delete>
    <select id="getEmpById" resultType="com.li.hellospringmybatis.pojo.Emp">
        select *
        from `emp`
        where id = #{id}
    </select>
    <select id="getEmps" resultType="com.li.hellospringmybatis.pojo.Emp">
        select *
        from `emp`
    </select>
    <update id="updateEmp">
        update `emp`
        set `name` = #{name},
        `age` = #{age},
        `salary` = #{salary}
        where id = #{id}
    </update>
</mapper>
```

- config mybatis files path in application.properties

```properties
mybatis.mapper-locations=classpath:mapper/**.xml
```

- usage of mapper

```java
package com.li.hellospringmybatis;

import com.li.hellospringmybatis.mapper.EmpMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloSpringMybatisApplicationTests {

    @Autowired
    EmpMapper empMapper;

    @Test
    void EmpMapperTest() {
//		get by id test
        System.out.println(empMapper.getEmpById(1));
//		get all test
        List<Emp> emps = empMapper.getEmps();
        System.out.println(emps);
//		add test
        empMapper.addEmp(new Emp("小明", 18, new BigDecimal(10000)));
        emps = empMapper.getEmps();
        System.out.println(emps);
//		delete test
        empMapper.deleteEmpById(emps.size());
        emps = empMapper.getEmps();
        System.out.println(emps);
//		update test
        Emp emp = empMapper.getEmpById(emps.size());
        System.out.println(emp);
        emp.setSalary(emp.getSalary().add(new BigDecimal(1000)));
        empMapper.updateEmp(emp);
        System.out.println(empMapper.getEmpById(emps.size()));
    }
}
```

# show sql in log

- 设置 MyBatis 日志级别为 DEBUG: `logging.level.org.mybatis=DEBUG`

- 或者更具体地设置 MyBatis 映射器的日志级别(`com.li.hellospringmybatis.mapper` is the package name of mappers):
  `logging.level.com.li.hellospringmybatis.mapper=DEBUG`

# useGeneratedKeys & keyProperty

- `useGeneratedKeys=true` will enable getting auto-increment id after insert
- `keyProperty="id"` will set the auto-increment id to the property named `id`

```xml

<insert id="addEmp" useGeneratedKeys="true" keyProperty="id">
    insert into `emp` (`name`, `age`, `salary`)
    values (#{name}, #{age}, #{salary})
</insert>
```

# camel case to underscore

- `mybatis.configuration.map-underscore-to-camel-case=true` to enable camel case to underscore

# `#{}` and `${}` in mybatis config sql

- `#{}` is used as pre-compile method sql pattern, it will be replaced by `?` in sql
- `${}` is used as string concatenation, it will concat to sql contents

- `${}` could not protect sql injection, the easiest way for `${}` check is ban the space in variable, re pattern could
  be used to.

- table name and col name could only use `${}`: `select ${colName} from ${tableName} where id = #{id}`

# @Param

- **if parameters decorated by `@Param("xxx")`, using `xxx` or `xxx.xxxx` is the only way to get it and its properties"
  **
- case 1 (basic type):
  ```java
  public User getUserById(@Param("id") int id, @Param("name") String name);
  ```
  ```xml
  <select id="getUserById" resultType="User">
      SELECT * FROM users WHERE id = #{id} AND name = #{name}
  </select>
  ```
- case 2 (Map or Object):
  ```java
  public User addUser(@Param("user") User u);
  public User addUser(@Param("user") Map<String, Object> u);
  ```
  ```xml
  <select id="addUser" resultType="User">
      INSERT INTO users (id, name, age) VALUES (#{user.id}, #{user.name}, #{user.age})
  </select>
  ```

- case 3 (List):
  ```java
  User xxx(@Param("indexes") List<Integer> ids);
  ```
  ```xml
  <select id="xxx" resultType="User">
      SELECT * FROM users WHERE id = #{indexes[0]}
  </select>
  ```

# returnType

## return List

- if return list, use the type of objects in list as `resultType`

```java
public List<User> getAllUsers();
```

```xml

<select id="getAllUsers" resultType="com.example.User">
    SELECT * FROM users
</select>
```

## return Map

- if return Map, use `@MapKey` to specify the key of Map
- if `resultType=java.util.Map`, the return type is `Map<???, Map>` even if method return type is `Map<???, User>`,
  the "User" is a `HashMap` and exception will threw
- if `resultType=xxx.xx.x.pojo.User`, the return type is `Map<???, User>`

```java

@MapKey("id")
Map<Integer, User> getUsersById();
```

```xml

<select id="getUsersById" resultType="com.example.User">
    SELECT * FROM users
</select>
```

# resultMap

```xml

<mapper namespace="xxx">
    <resultMap id="userResultMap" type="com.example.User">
        <id property="id" column="user_id"/>
        <result property="name" column="user_name"/>
        <result property="email" column="user_email"/>
    </resultMap>

    <select id="getUserById" resultMap="userResultMap">
        SELECT user_id, user_name, user_email FROM users WHERE user_id = #{id}
    </select>
</mapper>
```

## association

- 作用：用于映射一对一（1:1）的关系。
- 格式：在 <resultMap> 中使用 <association> 标签来定义关联对象的映射规则。
- 应用场景：适用于一个对象包含另一个对象的情况，例如用户和其地址。

```java
public class User {
    private Integer id;
    private String name;
    private Address address; // 一对一关联
    // getters and setters
}

public class Address {
    private Integer id;
    private String street;
    private String city;
    // getters and setters
}
```

```xml

<mapper namespace="xxx">
    <resultMap id="userResultMap" type="com.example.User">
        <id property="id" column="user_id"/>
        <result property="name" column="user_name"/>
        <association property="address" javaType="com.example.Address">
            <id property="id" column="address_id"/>
            <result property="street" column="address_street"/>
            <result property="city" column="address_city"/>
        </association>
    </resultMap>

    <select id="getUserById" resultMap="userResultMap">
        SELECT u.id AS user_id, u.name AS user_name,
        a.id AS address_id, a.street AS address_street, a.city AS address_city
        FROM users u
        LEFT JOIN addresses a ON u.address_id = a.id
        WHERE u.id = #{id}
    </select>
</mapper>
```

## collection

- 作用：用于映射一对多（1:N）或多对多（N:N）的关系。
- 格式：在 <resultMap> 中使用 <collection> 标签来定义集合属性的映射规则。
- 应用场景：适用于一个对象包含多个其他对象的情况，例如订单和订单项。

```java
public class Order {
    private Integer id;
    private String orderNumber;
    private List<OrderItem> items; // 一对多关联
    // getters and setters
}

public class OrderItem {
    private Integer id;
    private String itemName;
    private BigDecimal price;
    // getters and setters
}
```

```xml

<mapper namespace="xxx">
    <resultMap id="orderResultMap" type="com.example.Order">
        <id property="id" column="order_id"/>
        <result property="orderNumber" column="order_number"/>
        <collection property="items" ofType="com.example.OrderItem">
            <id property="id" column="item_id"/>
            <result property="itemName" column="item_name"/>
            <result property="price" column="item_price"/>
        </collection>
    </resultMap>

    <select id="getOrderById" resultMap="orderResultMap">
        SELECT o.id AS order_id, o.order_number AS order_number,
        i.id AS item_id, i.item_name AS item_name, i.price AS item_price
        FROM orders o
        LEFT JOIN order_items i ON o.id = i.order_id
        WHERE o.id = #{id}
    </select>
</mapper>
``` 


# MyBatis DTD
根据 MyBatis 的 DTD 规范，resultMap 的子元素必须按照特定的顺序排列。正确的顺序是：
1. constructor?
2. id*
3. result*
4. association*
5. collection*
6. discriminator?
