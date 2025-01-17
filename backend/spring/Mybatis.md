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
- !!! if name in sql not the same as bean, use alias in sql select

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
