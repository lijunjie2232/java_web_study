<!-- TOC -->

* [A Simple Mybatis Example](#a-simple-mybatis-example)
    * [@Mapper](#mapper)

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

@Mapper
public interface EmpMapper {
    Emp getEmpById(Integer id);
}

```

- create Mybatis xml configuration file for mapper
- !!! if name in sql not the same as bean, use alias in sql select

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.li.hellospringmybatis.mapper.EmpMapper">
    <select id="getEmpById" resultType="com.li.hellospringmybatis.pojo.Emp">
        select * from emp where id = #{id}
    </select>
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
import org.springframework.boot.test.context.SpringBootTest;[SpringMVC.md](SpringMVC.md)

@SpringBootTest
class HelloSpringMybatisApplicationTests {

    @Autowired
    EmpMapper empMapper;

    @Test
    void testEmpMapper() {
        System.out.println(empMapper.getEmpById(1));
    }

}
```
