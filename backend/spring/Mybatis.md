# A Simple Mybatis Example
- configure datasource as spring boot jdbc
```properties
spring.datasource.url=jdbc:mysql://localhost:13306/ssm3?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

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

