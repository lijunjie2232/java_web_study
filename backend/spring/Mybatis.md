<!-- TOC -->

* [A Simple Mybatis Example](#a-simple-mybatis-example)
    * [@Mapper](#mapper)
* [show sql in log](#show-sql-in-log)

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

@Select("SELECT * FROM users")
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