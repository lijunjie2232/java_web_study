<!-- TOC -->
* [Spring Boot auto configuration](#spring-boot-auto-configuration)
  * [example: change default data source](#example-change-default-data-source)
  * [启用调试日志](#启用调试日志)
  * [Spring Boot 自动配置的源码解析](#spring-boot-自动配置的源码解析)
    * [1. **`@SpringBootApplication` 注解**](#1-springbootapplication-注解)
    * [2. **`@EnableAutoConfiguration` 注解**](#2-enableautoconfiguration-注解)
      * [2.1 `@AutoConfigurationPackage`](#21-autoconfigurationpackage)
      * [2.2 `AutoConfigurationImportSelector`](#22-autoconfigurationimportselector)
    * [3. **`spring.factories` 文件**](#3-springfactories-文件)
    * [4. **条件注解**](#4-条件注解)
    * [5. **`@AutoConfigureAfter` 和 `@AutoConfigureBefore`**](#5-autoconfigureafter-和-autoconfigurebefore)
    * [6. **自动配置报告**](#6-自动配置报告)
    * [7. **排除自动配置类**](#7-排除自动配置类)
    * [8. **自定义自动配置类**](#8-自定义自动配置类)
* [@ConfigurationProperties](#configurationproperties)
* [@EnableConfigurationProperties](#enableconfigurationproperties)
* [常用的 `application.properties` 配置项](#常用的-applicationproperties-配置项)
  * [服务器配置](#服务器配置)
  * [应用程序配置](#应用程序配置)
      * [数据源配置](#数据源配置)
  * [日志配置](#日志配置)
  * [安全配置](#安全配置)
  * [模板引擎配置](#模板引擎配置)
  * [静态资源配置](#静态资源配置)
  * [跨域配置](#跨域配置)
  * [Programming Method](#programming-method)
* [Log](#log)
  * [A Simple Logging Example](#a-simple-logging-example)
  * [log level](#log-level)
    * [spring log level:](#spring-log-level)
  * [logging group](#logging-group)
  * [Output log to file](#output-log-to-file)
  * [log archive and split to chunks](#log-archive-and-split-to-chunks)
  * [string format in log](#string-format-in-log)
  * [change to log4j2](#change-to-log4j2)
* [spring environment isolation](#spring-environment-isolation)
  * [Outer properties](#outer-properties)
* [Unit Test](#unit-test)
  * [常用的注解](#常用的注解)
  * [assert](#assert)
    * [Example](#example)
* [sprint actuator](#sprint-actuator)
  * [1.import dependency](#1import-dependency)
  * [2.set properties like following:](#2set-properties-like-following)
  * [3. 常用的 Actuator 端点](#3-常用的-actuator-端点)
    * [3.1 `/actuator/health`](#31-actuatorhealth)
    * [3.2 `/actuator/info`](#32-actuatorinfo)
    * [3.3 `/actuator/metrics`](#33-actuatormetrics)
    * [3.4 `/actuator/metrics/{metricName}`](#34-actuatormetricsmetricname)
    * [3.5 `/actuator/loggers`](#35-actuatorloggers)
    * [3.6 `/actuator/loggers/{name}`](#36-actuatorloggersname)
    * [3.7 `/actuator/env`](#37-actuatorenv)
    * [3.8 `/actuator/beans`](#38-actuatorbeans)
    * [3.9 `/actuator/threaddump`](#39-actuatorthreaddump)
    * [3.10 `/actuator/httptrace`](#310-actuatorhttptrace)
  * [4. 安全配置](#4-安全配置)
    * [4.1 dependency](#41-dependency)
    * [4.2 配置 Spring Security](#42-配置-spring-security)
    * [4.3 配置 Actuator 端点的安全性](#43-配置-actuator-端点的安全性)
  * [5. 自定义 Actuator 端点](#5-自定义-actuator-端点)
    * [5.1 创建自定义端点](#51-创建自定义端点)
    * [5.2 公开自定义端点](#52-公开自定义端点)
    * [5.3 访问自定义端点](#53-访问自定义端点)
  * [6. 使用 Actuator 的 Web 管理界面](#6-使用-actuator-的-web-管理界面)
    * [6.1 添加 Spring Boot Admin 依赖](#61-添加-spring-boot-admin-依赖)
    * [6.2 配置 Spring Boot Admin](#62-配置-spring-boot-admin)
    * [6.3 启动 Spring Boot Admin 服务器](#63-启动-spring-boot-admin-服务器)
* [Spring boot life cycle Listener](#spring-boot-life-cycle-listener)
  * [SpringApplicationRunListener](#springapplicationrunlistener)
* [Spring boot life cycle](#spring-boot-life-cycle)
  * [常见的生命周期回调接口](#常见的生命周期回调接口)
* [spring Event](#spring-event)
* [Custom Spring Boot Starter](#custom-spring-boot-starter)
  * [1. 创建 Starter 项目](#1-创建-starter-项目)
    * [Maven 项目结构](#maven-项目结构)
  * [2. 配置 `pom.xml` 或 `build.gradle`](#2-配置-pomxml-或-buildgradle)
    * [Maven `pom.xml`](#maven-pomxml)
  * [3. 创建自动配置类](#3-创建自动配置类)
  * [4. 创建服务类](#4-创建服务类)
  * [5. 配置 `spring.factories`](#5-配置-springfactories)
  * [6. 打包并发布](#6-打包并发布)
    * [Maven 打包](#maven-打包)
  * [7. 使用自定义 Starter](#7-使用自定义-starter)
    * [Maven `pom.xml`](#maven-pomxml-1)
  * [示例代码](#示例代码)
      * [自定义 Starter 项目结构](#自定义-starter-项目结构)
    * [`MyService.java`](#myservicejava)
    * [`MyAutoConfiguration.java`](#myautoconfigurationjava)
    * [properties in META-INF](#properties-in-meta-inf)
    * [`pom.xml`](#pomxml)
    * [使用自定义 Starter 的项目结构](#使用自定义-starter-的项目结构)
    * [`DemoApplication.java`](#demoapplicationjava)
    * [`pom.xml`](#pomxml-1)
* [Use Redis in Spring Boot](#use-redis-in-spring-boot)
  * [Jedis](#jedis)
    * [dependency](#dependency)
    * [connect to redis](#connect-to-redis)
    * [基本操作](#基本操作)
    * [1. 连接到 Redis 服务器](#1-连接到-redis-服务器)
    * [2. 字符串操作](#2-字符串操作)
    * [3. 哈希操作](#3-哈希操作)
    * [4. 列表操作](#4-列表操作)
    * [5. 集合操作](#5-集合操作)
    * [6. 有序集合操作](#6-有序集合操作)
    * [7. 发布/订阅](#7-发布订阅)
    * [8. 事务和管道](#8-事务和管道)
      * [事务](#事务)
      * [管道](#管道)
  * [Lettuce](#lettuce)
    * [dependency](#dependency-1)
    * [setup](#setup)
    * [Usage](#usage)
    * [1. 连接到 Redis 服务器](#1-连接到-redis-服务器-1)
    * [2. 字符串操作](#2-字符串操作-1)
    * [3. 哈希操作](#3-哈希操作-1)
    * [4. 列表操作](#4-列表操作-1)
    * [5. 集合操作](#5-集合操作-1)
    * [6. 有序集合操作](#6-有序集合操作-1)
    * [7. 发布/订阅](#7-发布订阅-1)
    * [8. 事务和管道](#8-事务和管道-1)
      * [事务](#事务-1)
      * [管道](#管道-1)
  * [Redis Template](#redis-template)
    * [dependency](#dependency-2)
    * [setup](#setup-1)
    * [Usage](#usage-1)
    * [1. 字符串（String）操作](#1-字符串string操作)
    * [2. 哈希（Hash）操作](#2-哈希hash操作)
    * [3. 列表（List）操作](#3-列表list操作)
    * [4. 集合（Set）操作](#4-集合set操作)
    * [5. 有序集合（ZSet）操作](#5-有序集合zset操作)
    * [6. 删除操作](#6-删除操作)
    * [7. 判断键是否存在](#7-判断键是否存在)
    * [8. 设置过期时间](#8-设置过期时间)
    * [9. 获取键的剩余过期时间](#9-获取键的剩余过期时间)
    * [10. 递增/递减操作](#10-递增递减操作)
  * [Auto-refresh cluster view in RedisTemplate and Lettuce](#auto-refresh-cluster-view-in-redistemplate-and-lettuce)
  * [Sentinel](#sentinel)
<!-- TOC -->

# Spring Boot auto configuration

## example: change default data source

- method 1

```java

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }
}
```

- method 2

```properties
# application.properties
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
```

## 启用调试日志

- 在 `application.properties` 中添加以下配置以启用更多的调试信息：

```properties
logging.level.org.springframework.boot.autoconfigure=DEBUG
```

## Spring Boot 自动配置的源码解析

Spring Boot 的自动配置机制是其核心特性之一，它通过一系列复杂的类和注解来实现。理解其工作原理有助于更好地掌握如何自定义和调试自动配置。以下是
Spring Boot 自动配置的主要组成部分及其在源码中的实现。

![SpringBoot-AutoConfiguration.png](SpringBoot-AutoConfiguration.png)

### 1. **`@SpringBootApplication` 注解**

`@SpringBootApplication` 是 Spring Boot 应用程序中最常用的注解，它实际上是以下三个注解的组合：

- `@Configuration`：标记该类为一个配置类。
- `@EnableAutoConfiguration`：启用自动配置。
- `@ComponentScan`：扫描组件，默认从当前包及其子包中查找组件。

```java

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
        @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)
})
public @interface SpringBootApplication {
    // ...
}
```

### 2. **`@EnableAutoConfiguration` 注解**

`@EnableAutoConfiguration` 是自动配置的核心注解，它会触发自动配置类的加载。具体来说，它会读取 `META-INF/spring.factories`
文件，并根据条件选择合适的配置类。

```java

@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {
    // ...
}
```

#### 2.1 `@AutoConfigurationPackage`

`@AutoConfigurationPackage` 注解的作用是将主应用程序类所在的包注册为默认的包扫描路径。这样，Spring Boot 可以在该包及其子包中查找组件。

```java

@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {
}
```

#### 2.2 `AutoConfigurationImportSelector`

`AutoConfigurationImportSelector` 类是自动配置的核心部分，它负责加载 `spring.factories` 文件中的自动配置类。具体来说，它实现了
`ImportSelector` 接口，并重写了 `selectImports` 方法。

```java
public class AutoConfigurationImportSelector implements ImportSelector, DeferredImportSelector, BeanClassLoaderAware, ResourceLoaderAware {
    // ...
}
```

- `selectImports` 方法的工作流程如下：
    1. **读取 `spring.factories` 文件**：从 `META-INF/spring.factories` 文件中读取所有自动配置类的全限定名。
    2. **过滤自动配置类**：根据条件注解（如 `@ConditionalOnClass`, `@ConditionalOnMissingBean`, `@ConditionalOnProperty`
       等）过滤出符合条件的自动配置类。
    3. **返回自动配置类列表**：将符合条件的自动配置类作为字符串数组返回给 Spring 容器，以便创建相应的 Bean。

### 3. **`spring.factories` 文件**

`spring.factories` 文件位于 `META-INF/` 目录下，用于声明自动配置类。例如：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
...
```

Spring Boot 启动时会读取这个文件并加载其中列出的自动配置类。

### 4. **条件注解**

自动配置类通常使用条件注解来决定是否应用某个配置。常见的条件注解包括：

- `@ConditionalOnClass`：只有当指定的类存在时才应用配置。
- `@ConditionalOnMissingBean`：只有当没有特定类型的 Bean 存在时才应用配置。
- `@ConditionalOnProperty`：根据配置文件中的属性值决定是否应用配置。
- `@ConditionalOnWebApplication`：只有在 Web 应用程序中才应用配置。
- `@ConditionalOnNotWebApplication`：只有在非 Web 应用程序中才应用配置。

例如，`DataSourceAutoConfiguration` 类使用了这些条件注解：

```java

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
@EnableConfigurationProperties(DataSourceProperties.class)
@Import({DataSourcePoolMetadataProvidersConfiguration.class, DataSourceInitializationConfiguration.class})
public class DataSourceAutoConfiguration {
    // ...
}
```

### 5. **`@AutoConfigureAfter` 和 `@AutoConfigureBefore`**

这些注解用于控制自动配置类的加载顺序。例如，`HibernateJpaAutoConfiguration` 需要在 `DataSourceAutoConfiguration` 之后加载：

```java

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({EntityManager.class, EntityManagerFactory.class})
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class HibernateJpaAutoConfiguration {
    // ...
}
```

### 6. **自动配置报告**

Spring Boot 提供了一个自动配置报告，可以在启动时输出到控制台，可以通过以下方式启用：

```properties
debug=true
```

这将显示哪些自动配置类被应用，哪些被排除，以及原因。

### 7. **排除自动配置类**

如果需要禁用某些自动配置类，可以在主应用程序类或配置类中使用 `@SpringBootApplication` 注解的 `exclude` 属性：

```java

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

或者直接在 `application.properties` 或 `application.yml` 中配置：

```properties
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
```

### 8. **自定义自动配置类**

创建自己的自动配置类可以参考以下步骤：

1. **创建配置类**：编写一个带有 `@Configuration` 注解的类。
2. **添加条件注解**：根据需要添加条件注解，确保配置类仅在特定条件下生效。
3. **注册自动配置类**：将自动配置类添加到 `META-INF/spring.factories` 文件中。

例如：

```java

@Configuration
@ConditionalOnClass(RedisOperations.class)
@ConditionalOnProperty(prefix = "myapp.redis", name = "enabled", havingValue = "true")
public class RedisAutoConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        return template;
    }
}
```

然后在 `META-INF/spring.factories` 中添加：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.RedisAutoConfiguration
```

# @ConfigurationProperties

- Bind Bean properties with Spring Boot Properties

```properties
abc.a=a
abc.b=b
abc.c=c
```

```java
package com.li.hellospringbootbase1.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "abc")
public class MyAutoConfiguration {
    private String a;
    private String b;
    private String c;
}

```

# @EnableConfigurationProperties

- annotate spring boot application class with `@EnableConfigurationProperties(xxx.class)`
  to add xxx.class to spring context and enable `@ConfigurationProperties` in xxx.class
  if xxx.class is not annotated with `@Component`

在Spring Boot应用程序中，`application.properties` 或 `application.yml` 文件用于配置各种属性。以下是一些常用的配置项及其用途：

# 常用的 `application.properties` 配置项

## 服务器配置

- **服务器端口**
  ```properties
  server.port=8080
  ```


- **服务器上下文路径**
  ```properties
  server.servlet.context-path=/myapp
  ```


- **最大线程数**
  ```properties
  server.tomcat.max-threads=200
  ```


- **连接超时时间**
  ```properties
  server.connection-timeout=20000
  ```

## 应用程序配置

- **应用程序名称**
  ```properties
  spring.application.name=my-application
  ```


- **激活的配置文件**
  ```properties
  spring.profiles.active=dev
  ```

#### 数据源配置

- **JDBC URL**
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/mydb
  ```


- **用户名和密码**
  ```properties
  spring.datasource.username=root
  spring.datasource.password=root
  ```


- **驱动类名**
  ```properties
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  ```


- **连接池配置**
  ```properties
  spring.datasource.hikari.maximum-pool-size=10
  spring.datasource.hikari.minimum-idle=5
  spring.datasource.hikari.idle-timeout=30000
  spring.datasource.hikari.max-lifetime=1800000
  ```

## 日志配置

- **日志级别**
  ```properties
  logging.level.root=INFO
  logging.level.org.springframework.web=DEBUG
  logging.level.com.example.demo=TRACE
  ```


- **日志文件路径**
  ```properties
  logging.file.name=app.log
  ```

## 安全配置

- **启用安全**
  ```properties
  spring.security.enabled=true
  ```


- **默认用户名和密码**
  ```properties
  spring.security.user.name=user
  spring.security.user.password=password
  ```

## 模板引擎配置

- **Thymeleaf 缓存**
  ```properties
  spring.thymeleaf.cache=false
  ```


- **模板文件路径**
  ```properties
  spring.thymeleaf.prefix=classpath:/templates/
  spring.thymeleaf.suffix=.html
  ```

## 静态资源配置

- **静态资源路径**
  ```properties
  spring.resources.static-locations=classpath:/static/,classpath:/public/,classpath:/resources/,classpath:/META-INF/resources/
  ```

## 跨域配置

- **允许的来源**
  ```properties
  spring.mvc.cors.allowed-origins=http://example.com
  ```


- **允许的方法**
  ```properties
  spring.mvc.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
  ```


- **允许的头信息**
  ```properties
  spring.mvc.cors.allowed-headers=*
  ```


- **是否允许凭证**
  ```properties
  spring.mvc.cors.allow-credentials=true
  ```

## Programming Method

```java
package com.li.hellospringbootbase1;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class HelloSpringbootBase1Application {

    public static void main(String[] args) {

//        method 1
        SpringApplication.run(HelloSpringbootBase1Application.class, args);
//        method 2
        SpringApplication springApplication = new SpringApplication(HelloSpringbootBase1Application.class);
        springApplication.setLazyInitialization(true);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.setEnvironment(null);
        springApplication.run(args);
//        method 3
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder
                .sources(HelloSpringbootBase1Application.class)
                .bannerMode(Banner.Mode.OFF)
                .environment(null)
                .lazyInitialization(true)
                .run(args);

    }

}
```

# Log

## A Simple Logging Example

```java
package com.li.hellospringbootbase1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class LogTest {

    //    Logger log = LoggerFactory.getLogger(LogTest.class);// = @Slf4j
    @Test
    void logTest() {


        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }
}

```

## log level

- default log level is `info`
- change default log level in `application.properties` by setting `logging.level.root=debug`
- change log level for specific package by setting `logging.level.com.li.hellospringbootbase1=debug`

### spring log level:

- trace
- debug
- info
- warn
- error
- fatal
- off

## logging group

```properties
logging.group.group-name=com.li.hellospringbootbase1.service,com.li.hellospringbootbase1.dao
logging.level.group-name=debug
```

## Output log to file

- set `logging.file.name` **or** `logging.file.path` in spring properties
- `logging.file.path=[directory path]`
- `logging.file.name=[log file path]`
- `logging.file.name` has higher priority than `logging.file.path`

## log archive and split to chunks

- logback

```xml
<!--logback-spring.xml-->
<configuration>
    <!-- 定义日志文件路径和文件名 -->
    <property name="LOG_PATH" value="logs"/>
    <property name="LOG_FILE" value="my-application.log"/>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 当前日志文件路径 -->
        <file>${LOG_PATH}/${LOG_FILE}</file>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level - %msg%n</pattern>
        </encoder>

        <!-- 滚动策略 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 归档日志文件路径和命名模式 -->
            <fileNamePattern>${LOG_PATH}/my-application-%d{yyyy-MM-dd}.%i.log.gz</fileNamePattern>
            <!-- 最大历史日志文件数量 -->
            <maxHistory>30</maxHistory>
            <!-- 按大小分割日志文件 -->
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
    </appender>

    <root level="INFO">
        <appender-ref ref="FILE"/>
    </root>
</configuration>

```

- log4j2

```xml
<!--log4j2-spring.xml -->
<Configuration status="WARN">
    <Properties>
        <Property name="LOG_PATH">${sys:LOG_PATH}</Property>
        <Property name="LOG_FILE">${sys:LOG_FILE}</Property>
        <Property name="LOG_MAX_SIZE">${sys:LOG_MAX_SIZE}</Property>
        <Property name="LOG_MAX_HISTORY">${sys:LOG_MAX_HISTORY}</Property>
    </Properties>

    <Appenders>
        <RollingFile name="RollingFile" fileName="${LOG_PATH}/${LOG_FILE}"
                     filePattern="${LOG_PATH}/my-application-%d{yyyy-MM-dd}-%i.log.gz">
            <PatternLayout>
                <Pattern>%d{yyyy-MM-dd HH:mm:ss} - %msg%n</Pattern>
            </PatternLayout>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="${LOG_MAX_SIZE}"/>
            </Policies>
            <DefaultRolloverStrategy max="${LOG_MAX_HISTORY}"/>
        </RollingFile>
    </Appenders>

    <Loggers>
        <Root level="info">
            <AppenderRef ref="RollingFile"/>
        </Root>
    </Loggers>
</Configuration>

```

- spring properties

```properties
# log archive filename pattern
logging.logback.rollingpolicy.file-name-pattern=${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz
# if clean history log archive
logging.logback.rollingpolicy.clean-history-on-start=true
# max size of single log archive file
logging.logback.rollingpolicy.max-file-size=10MB
# max size of total log archives, 0B is no limit
logging.logback.rollingpolicy.total-size-cap=300MB
# reserve days of history log archives
logging.logback.rollingpolicy.max-history=30
```

## string format in log

```java
void logTest() {
    int a = 1;
    log.info("abc.a={}", a);
}
```

## change to log4j2

- exclusion `spring-boot-starter-logging` in `spring-boot-starter-web`
- add `spring-boot-starter-log4j2`

```xml

<dependencies>
    <!-- 其他依赖 -->

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

    <!-- 添加Log4j2依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>

    <!-- 其他依赖 -->
</dependencies>

```

# spring environment isolation

- touch and modify `application-xxx.properties` to set environment specific properties
- annotated bean and components with `@Profile("xxx")` to enable them in xxx environment and disable them in other
  different environments

- activate environment `xxx`:
    - method 1: set `spring.profiles.active=xxx` in `application.properties` to set current environment, then all the
      setting belongs
      to `xxx` environment has higher priority than that belongs to default environment
    - method 2: `java -jar springProject.jar --spring.profiles.active=xxx` on command line
- `spring.profiles.include=xxx1,xxx2` to include xxx1 and xxx2 environments whatever the current environment is
- `spring.profiles.groups.xx[0]=xxx1` and `spring.profiles.groups.xx[1]=xxx2` to group xxx1 and xxx2 environments as xxx
  group, then `spring.profiles.active=xx` will activate xxx1 and xxx2 environments

## Outer properties

- all valid outer `application.properties` path:
    ```
    - deploy_dir
        - config
            - xxx
                - application.properties
            - application.properties
        - app.jar
        - application.properties
    ```

- priority of properties:
    - (outer) config/xxx/application.properties (highest)
    - (outer) config/application.properties
    - (outer) application.properties
    - (inner) app.jar/application.properties (lowest)
    - properties in <text style="color:orange">inner activated</text> environments have <text style="color:red">
      higher</text> priority than <text style="color:orange">outer default</text> properties

# Unit Test

## 常用的注解

- **`@SpringBootTest`**：
    - 用于标注测试类，它会启动整个Spring应用上下文，适用于集成测试。
    - 可以通过`classes`属性指定加载的配置类，默认是主应用类。
    - 支持不同的webEnvironment类型，如定义是否启动mock或真实的Servlet环境。

- **`@Test`**：
    - 来自JUnit Jupiter，用于标注测试方法，表示该方法是一个测试用例。

- **`@DisplayName("test name")`**：
    - 同样来自JUnit Jupiter，为测试类或测试方法设置一个可读的名字，替代默认的方法名显示。

- **`@BeforeEach`** 和 **`@AfterEach`**：
    - 分别在每个测试方法执行之前和之后运行，可用于设置前置条件或清理工作。

- **`@BeforeAll`** 和 **`@AfterAll`**：
    - 在所有测试方法执行之前和之后各执行一次，通常用于一次性初始化或资源释放操作。

```java
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {

    // 用于存储测试数据
    private static StringBuilder output;

    // 在所有测试方法执行之前运行一次
    @BeforeAll
    static void setUpBeforeAll() {
        System.out.println("Running @BeforeAll");
        output = new StringBuilder();
    }

    // 在所有测试方法执行之后运行一次
    @AfterAll
    static void tearDownAfterAll() {
        System.out.println("Running @AfterAll");
        output = null;
    }

    // 在每个测试方法执行之前运行
    @BeforeEach
    void setUpBeforeEach() {
        System.out.println("Running @BeforeEach");
        output.setLength(0); // 清空StringBuilder
    }

    // 在每个测试方法执行之后运行
    @AfterEach
    void tearDownAfterEach() {
        System.out.println("Running @AfterEach");
        System.out.println("Final output: " + output.toString());
    }

    @Test
    @DisplayName("Test method 1")
    void testMethod1() {
        System.out.println("Running testMethod1");
        output.append("Hello");
        assertEquals("Hello", output.toString());
    }

    @Test
    @DisplayName("Test method 2")
    void testMethod2() {
        System.out.println("Running testMethod2");
        output.append("World");
        assertEquals("World", output.toString());
    }
}

```

## assert

- `assertEquals`
- `assertNotEquals`
- `assertSame`
- `assertNotSame`
- `assertTrue`
- `assertFalse`
- `assertNull`
- `assertNotNull`
- `assertArrayEquals`
- `assertAll`
- `assertThrows`
- `assertTimeout`
- `fail`


- `assertSame` and `assertNotSame` 验证两个对象<text style="color:orange">引用同一个对象</text>
  ```java
  Object obj1 = new Object();
  Object obj2 = new Object();
  assertNotSame(obj1, obj2); // 通过

  Object obj3 = obj1;
  assertNotSame(obj1, obj3); // 失败
  ```

### Example

```java
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {

    @Test
    void testAssertions() {
        int a = 10;
        int b = 5;
        String str1 = "Hello";
        String str2 = "World";
        Object obj1 = new Object();
        Object obj2 = obj1;

        assertEquals(a, 10);
        assertNotEquals(a, b);
        assertTrue(a > b);
        assertFalse(a < b);
        assertNull(null);
        assertNotNull(obj1);
        assertSame(obj1, obj2);
        assertNotSame(obj1, new Object());

        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Invalid argument");
        });

        assertDoesNotThrow(() -> {
            // 无异常代码
        });

        assertAll(
                () -> assertEquals(a, 10),
                () -> assertEquals(str1, "Hello"),
                () -> assertTrue(a > b)
        );
    }
}

```

# sprint actuator

## 1.import dependency

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## 2.set properties like following:

- `management.endpoints.web.exposure.include=*`
- `management.endpoints.web.exposure.include=health,info,metrics`

## 3. 常用的 Actuator 端点

### 3.1 `/actuator/health`

- **用途**：检查应用程序的健康状况。

### 3.2 `/actuator/info`

- **用途**：提供应用程序的详细信息。
- **配置示例**：

```properties
# application.properties
info.app.name=MyApp
info.app.description=My Spring Boot Application
info.app.version=1.0.0
```

### 3.3 `/actuator/metrics`

- **用途**：提供应用程序的各种度量指标。

### 3.4 `/actuator/metrics/{metricName}`

- **用途**：获取特定度量指标的详细信息。
- **示例**：获取 `http.server.requests` 指标的详细信息。

### 3.5 `/actuator/loggers`

- **用途**：查看和修改应用程序的日志级别。

### 3.6 `/actuator/loggers/{name}`

- **用途**：查看和修改特定日志记录器的日志级别。
- **示例**：查看 `com.example` 日志记录器的日志级别。

### 3.7 `/actuator/env`

- **用途**：查看应用程序的环境属性。

### 3.8 `/actuator/beans`

- **用途**：查看应用程序中的所有 Spring Bean。

### 3.9 `/actuator/threaddump`

- **用途**：生成应用程序的线程转储。

### 3.10 `/actuator/httptrace`

- **用途**：查看最近的 HTTP 请求和响应。

## 4. 安全配置

### 4.1 dependency

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 4.2 配置 Spring Security

创建一个配置类来保护 Actuator 端点：

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/actuator/**").hasRole("ACTUATOR")
                                .anyRequest().authenticated()
                )
                .httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("ACTUATOR")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
```

### 4.3 配置 Actuator 端点的安全性

- 确保 Actuator 端点的安全配置生效：

```properties
# application.properties
management.endpoints.web.exposure.include=*
```

## 5. 自定义 Actuator 端点

### 5.1 创建自定义端点

创建一个类并使用 `@Endpoint` 注解：

```java
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "custom")
public class CustomEndpoint {

    @ReadOperation
    public String customOperation() {
        return "Custom Endpoint Response";
    }
}
```

### 5.2 公开自定义端点

确保自定义端点被公开：

```properties
# application.properties
management.endpoints.web.exposure.include=*
```

### 5.3 访问自定义端点

访问自定义端点：

```
GET /actuator/custom
```

响应示例：

```json
"Custom Endpoint Response"
```

## 6. 使用 Actuator 的 Web 管理界面

Spring Boot Actuator 还提供了一个 Web 管理界面，可以方便地查看和管理应用程序的状态。

### 6.1 添加 Spring Boot Admin 依赖

在 `pom.xml` 文件中添加 Spring Boot Admin 的依赖：

```xml

<dependencies>
    <!-- 其他依赖 -->

    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-client</artifactId>
        <version>2.7.6</version>
    </dependency>

    <!-- 其他依赖 -->
</dependencies>
```

### 6.2 配置 Spring Boot Admin

在 `application.properties` 或 `application.yml` 文件中配置 Spring Boot Admin 的 URL：

```properties
# application.properties
spring.boot.admin.client.url=http://localhost:8081
management.endpoints.web.exposure.include=*
```

### 6.3 启动 Spring Boot Admin 服务器

可以使用 Spring Boot Admin 的 Starter 来启动一个管理服务器：

```xml

<dependencies>
    <!-- 其他依赖 -->

    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-server</artifactId>
        <version>2.7.6</version>
    </dependency>

    <!-- 其他依赖 -->
</dependencies>
```

创建一个 Spring Boot 应用程序类并添加 `@EnableAdminServer` 注解：

```java
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class AdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
}
```

启动管理服务器后，可以通过浏览器访问 `http://localhost:8081` 来查看和管理应用程序。

# Spring boot life cycle Listener

![SpringBoot-cycle.png](SpringBoot-cycle.png)

## SpringApplicationRunListener

```java
package com.li.hellospringbootbase1.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;

@Slf4j
public class MyListener implements SpringApplicationRunListener {
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        log.info("[MyListener] starting");
    }

    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        log.info("[MyListener] environmentPrepared");
    }

    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("[MyListener] contextPrepared");
    }

    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("[MyListener] contextLoaded");
    }

    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        log.info("[MyListener] started");
    }

    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        log.info("[MyListener] ready");
    }

    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("[MyListener] failed");
    }

}

```

- config in resources/META-INF/spring.factories

```properties
org.springframework.boot.SpringApplicationRunListener=com.li.hellospringbootbase1.listener.MyListener
```

# Spring boot life cycle

![SpringBoot-cycle-f.png](SpringBoot-cycle-f.png)

Spring Boot 应用的生命周期可以分为几个主要阶段，每个阶段都有特定的事件和回调方法。以下是 Spring Boot 生命周期的主要阶段：

1. **启动准备阶段 (Bootstrap)**

- 读取配置文件。
- 初始化 `ApplicationContext`。

2. **上下文刷新阶段 (Context Refresh)**

- 加载并解析配置类。
- 注册 Bean 定义。
- 初始化所有 Singleton Beans（非懒加载）。
- 发布 `ContextRefreshedEvent` 事件。

3. **Web 环境准备阶段 (Web Environment Preparation)**

- 如果是 Web 应用，则创建并配置 `Servlet` 容器。

4. **应用运行阶段 (Application Running)**

- 应用程序开始处理请求。
- 可以通过实现 `ApplicationRunner` 或 `CommandLineRunner` 接口，在应用启动完成后执行特定逻辑。

5. **关闭阶段 (Shutdown)**

- 当应用程序关闭时，会调用 `DisposableBean` 的 `destroy()` 方法或 `@PreDestroy` 注解的方法。
- 发布 `ContextClosedEvent` 事件。

## 常见的生命周期回调接口

- **`ApplicationListener`**：监听特定的 Spring Boot 事件，如 `ApplicationStartedEvent`、`ApplicationReadyEvent` 等。
- **`SmartLifecycle`**：提供更细粒度的控制，可以定义启动和停止的顺序。
- **`ApplicationRunner` 和 `CommandLineRunner`**：在应用启动后执行特定任务，区别在于 `ApplicationRunner` 支持
  `ApplicationArguments` 参数。

# spring Event

- event

```java
package com.li.hellospringbootbase1.event;

@Data
public class UserSigninEvent {
    private String message;

    public UserSigninEvent(String message) {
        this.message = message;
    }

}
```

- instant event and public event via ApplicationEventPublisher

```java
package com.li.hellospringbootbase1.controller;

@RequestMapping("/v3/api")
@RestController
public class UserController {


    @Autowired
    ApplicationEventPublisher publisher;

    @RequestMapping("/signin")
    public String signin(@RequestParam("username") String username) {
        publisher.publishEvent(new UserSigninEvent(username + " 用户登录"));

        return "{\"message\": \"success\", \"code\": 200}";
    }

}
```

- listen event by `@EventListener(UserSigninEvent.class)`

```java
package com.li.hellospringbootbase1.service;

@Slf4j
@Service
public class UserService {

    @Async
    @EventListener(UserSigninEvent.class)
    public void signin(UserSigninEvent event) {
        log.info("[user signin event]: " + event.getMessage());
    }
}

```

# Custom Spring Boot Starter

创建自定义的 Spring Boot Starter 是一种强大的方式，可以封装和分发可重用的配置和功能。通过自定义
Starter，你可以简化项目的配置，使其更加模块化和易于维护。以下是创建自定义 Spring Boot Starter 的步骤：

## 1. 创建 Starter 项目

首先，创建一个新的 Maven 或 Gradle 项目，用于存放你的 Starter。

### Maven 项目结构

```
my-custom-starter/
├── pom.xml
└── src/
    └── main/
        └── resources/
            └── META-INF/
                └── spring.factories
```

## 2. 配置 `pom.xml` 或 `build.gradle`

确保你的项目依赖于 `spring-boot-starter`，并且打包类型为 `jar`。

### Maven `pom.xml`

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>my-custom-starter</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.5</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <!-- 添加其他依赖 -->
    </dependencies>
</project>
```

## 3. 创建自动配置类

创建一个自动配置类，使用 `@Configuration` 和 `@ConditionalOnClass` 等注解来定义自动配置逻辑。

```java
package com.example.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MyService.class)
public class MyAutoConfiguration {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

## 4. 创建服务类

创建一个简单的服务类，该类将在自动配置中被实例化。

```java
package com.example.service;

public class MyService {

    public void doSomething() {
        System.out.println("Doing something in MyService");
    }
}
```

## 5. 配置 `spring.factories`

在 `src/main/resources/META-INF/spring.factories` 文件中注册自动配置类。

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.config.MyAutoConfiguration
```

## 6. 打包并发布

使用 Maven 或 Gradle 打包你的 Starter，并将其发布到 Maven 仓库或本地仓库。

### Maven 打包

```sh
mvn clean install
```

## 7. 使用自定义 Starter

在你的 Spring Boot 项目中引入自定义的 Starter。

### Maven `pom.xml`

```xml

<dependency>
    <groupId>com.example</groupId>
    <artifactId>my-custom-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 示例代码

以下是一个完整的示例，展示了如何创建和使用自定义的 Spring Boot Starter。

#### 自定义 Starter 项目结构

```
my-custom-starter/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    ├── config/
                    │   └── MyAutoConfiguration.java
                    └── service/
                        └── MyService.java
        └── resources/
            └── META-INF/
                └── spring.factories
```

### `MyService.java`

```java
package com.example.service;

public class MyService {

    public void doSomething() {
        System.out.println("Doing something in MyService");
    }
}
```

### `MyAutoConfiguration.java`

```java
package com.example.config;

import com.example.service.MyService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MyService.class)
public class MyAutoConfiguration {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

### properties in META-INF

- method 1: touch `META-INF/spring.factories`

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.config.MyAutoConfiguration
```

- method 2: touch file `META-INF.spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`

```properties
com.example.config.MyAutoConfiguration
```

### `pom.xml`

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>my-custom-starter</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.5</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
    </dependencies>
</project>
```

### 使用自定义 Starter 的项目结构

```
demo/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    └── demo/
                        └── DemoApplication.java
```

### `DemoApplication.java`

```java
package com.example.demo;

import com.example.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private MyService myService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        myService.doSomething();
    }
}
```

### `pom.xml`

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.5</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.example</groupId>
            <artifactId>my-custom-starter</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
</project>
```

# Use Redis in Spring Boot

## Jedis

### dependency

```xml
<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>5.2.0</version>
</dependency>
```

### connect to redis

- timeout should be set if net work is not stable

```java

@Component
public class JedisUtil {
    @Value("${redis.host:127.0.0.1}")
    String host;
    @Value("${redis.port:6379}")
    String port;
    @Value("${redis.password:redis}")
    private String password;

    private Jedis jedis;

    @PostConstruct
    public void init() {
        System.out.println("*******************JedisUtil init*******************");
        System.out.println("host: " + host);
        System.out.println("port: " + port);
        System.out.println("password: " + password);
        this.jedis = new Jedis(host, Integer.parseInt(port), 10000);
        this.jedis.auth(password);
    }
}
```

### 基本操作

1. **连接到 Redis 服务器**
2. **字符串操作**
3. **哈希操作**
4. **列表操作**
5. **集合操作**
6. **有序集合操作**
7. **发布/订阅**
8. **事务和管道**

### 1. 连接到 Redis 服务器

```java
import redis.clients.jedis.Jedis;

public class JedisExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("redis");
        System.out.println("Connected to Redis server successfully");
        jedis.close();
    }
}
```

### 2. 字符串操作

```java
import redis.clients.jedis.Jedis;

public class JedisExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("redis");

        // 设置字符串
        jedis.set("name", "Alice");
        System.out.println("Stored string in Redis:: " + jedis.get("name"));

        jedis.close();
    }
}
```

### 3. 哈希操作

```java
import redis.clients.jedis.Jedis;

public class JedisExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("redis");

        // 设置哈希
        jedis.hset("user:1000", "name", "Alice");
        jedis.hset("user:1000", "age", "25");

        // 获取哈希
        System.out.println("Stored hash in Redis:: " + jedis.hget("user:1000", "name"));
        System.out.println("Stored hash in Redis:: " + jedis.hget("user:1000", "age"));

        jedis.close();
    }
}
```

### 4. 列表操作

```java
import redis.clients.jedis.Jedis;

public class JedisExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("redis");

        // 添加到列表
        jedis.lpush("list1", "item1");
        jedis.lpush("list1", "item2");
        jedis.lpush("list1", "item3");

        // 获取列表
        System.out.println("Stored list in Redis:: " + jedis.lrange("list1", 0, -1));

        jedis.close();
    }
}
```

### 5. 集合操作

```java
import redis.clients.jedis.Jedis;

public class JedisExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("redis");

        // 添加到集合
        jedis.sadd("set1", "item1");
        jedis.sadd("set1", "item2");
        jedis.sadd("set1", "item3");

        // 获取集合
        System.out.println("Stored set in Redis:: " + jedis.smembers("set1"));

        jedis.close();
    }
}
```

### 6. 有序集合操作

```java
import redis.clients.jedis.Jedis;

public class JedisExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("redis");

        // 添加到有序集合
        jedis.zadd("zset1", 1, "item1");
        jedis.zadd("zset1", 2, "item2");
        jedis.zadd("zset1", 3, "item3");

        // 获取有序集合
        System.out.println("Stored sorted set in Redis:: " + jedis.zrange("zset1", 0, -1));

        jedis.close();
    }
}
```

### 7. 发布/订阅

```java
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisExample {
    public static void main(String[] args) {
        // 订阅线程
        new Thread(() -> {
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            jedis.auth("redis");

            JedisPubSub jedisPubSub = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    System.out.println("Received message from channel '" + channel + "': " + message);
                }
            };

            jedis.subscribe(jedisPubSub, "channel1");
            jedis.close();
        }).start();

        // 发布线程
        new Thread(() -> {
            Jedis jedis = new Jedis("127.0.0.1", 6379);
            jedis.auth("redis");

            jedis.publish("channel1", "Hello, Redis!");
            jedis.close();
        }).start();
    }
}
```

### 8. 事务和管道

#### 事务

```java
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class JedisExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("redis");

        Transaction tx = jedis.multi();
        tx.set("foo", "bar");
        tx.incr("counter");
        tx.exec();

        System.out.println("foo: " + jedis.get("foo"));
        System.out.println("counter: " + jedis.get("counter"));

        jedis.close();
    }
}
```

#### 管道

```java
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class JedisExample {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("redis");

        Pipeline pipeline = jedis.pipelined();
        pipeline.set("foo", "bar");
        pipeline.incr("counter");
        pipeline.sync();

        System.out.println("foo: " + jedis.get("foo"));
        System.out.println("counter: " + jedis.get("counter"));

        jedis.close();
    }
}
```

## Lettuce

### dependency

```xml
<!-- https://mvnrepository.com/artifact/io.lettuce/lettuce-core -->
<dependency>
    <groupId>io.lettuce</groupId>
    <artifactId>lettuce-core</artifactId>
    <version>6.5.3.RELEASE</version>
</dependency>
```

### setup

```java
public void _init(
        @Value("${redis.host:127.0.0.1}") String host,
        @Value("${redis.port:6379}") Integer port,
        @Value("${redis.password:redis}") String password
) {
    System.out.println("*******************LettuceUtil init*******************");
    RedisURI uri = RedisURI.Builder
            .redis(host)
            .withPort(port)
            .withAuthentication("default", password)
            .withTimeout(Duration.ofSeconds(10))
            .build();
    this.client = RedisClient.create(uri);
    System.out.println("*******************LettuceUtil init*******************");
}
```

### Usage

```java

@Test
public void testJedisUtil() {
    try (StatefulRedisConnection connection = lu.getConnection()) {
        RedisCommands commands = connection.sync();
        System.out.println(commands.get("user"));

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
```

### 1. 连接到 Redis 服务器

首先确保已经建立了连接：

```java
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class LettuceExample {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6379;
    private static final String PASSWORD = "redis";

    public static void main(String[] args) {
        RedisClient client = RedisClient.create("redis://" + PASSWORD + "@" + HOST + ":" + PORT);
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisCommands<String, String> commands = connection.sync();

        // 执行命令...

        connection.close();
        client.shutdown();
    }
}
```

### 2. 字符串操作

- **设置和获取字符串**

```java
commands.set("key","value");

String value = commands.get("key");
System.out.

println("Stored string in Redis:: "+value);
```

- **追加字符串**

```java
commands.append("key","more");
```

- **递增/递减数值**

```java
commands.incr("counter"); // 自增
commands.

decr("counter"); // 自减
```

### 3. 哈希操作

- **设置哈希字段**

```java
commands.hset("hashKey","field1","value1");
```

- **获取哈希字段**

```java
String fieldValue = commands.hget("hashKey", "field1");
```

- **获取所有字段和值**

```java
Map<String, String> hashValues = commands.hgetall("hashKey");
```

### 4. 列表操作

- **向列表左侧插入元素**

```java
commands.lpush("listKey","item1","item2");
```

- **从列表右侧弹出元素**

```java
String item = commands.rpop("listKey");
```

- **获取列表范围内的元素**

```java
List<String> listItems = commands.lrange("listKey", 0, -1);
```

### 5. 集合操作

- **添加到集合**

```java
commands.sadd("setKey","member1","member2");
```

- **检查成员是否存在**

```java
Boolean isMember = commands.sismember("setKey", "member1");
```

- **获取集合中所有成员**

```java
Set<String> setMembers = commands.smembers("setKey");
```

### 6. 有序集合操作

- **添加到有序集合**

```java
commands.zadd("zsetKey",1.0,"member1");
```

- **获取指定范围的成员（按分数排序）**

```java
List<String> zsetMembers = commands.zrange("zsetKey", 0, -1);
```

- **获取成员的排名**

```java
Long rank = commands.zrank("zsetKey", "member1");
```

### 7. 发布/订阅

Lettuce 提供了异步的支持来处理发布/订阅模式：

```java
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

// 订阅者
StatefulRedisPubSubConnection<String, String> pubSubConnection = client.connectPubSub();
        RedisPubSubCommands<String, String> pubSub = pubSubConnection.sync();
pubSub.

        subscribe("channel1");

pubSub.

        addListener(new RedisPubSubListener<String, String>() {
            @Override
            public void message (String channel, String message){
                System.out.println("Received message from channel '" + channel + "': " + message);
            }
        });

// 发布者
        commands.

        publish("channel1","Hello, Redis!");
```

### 8. 事务和管道

#### 事务

```java
import io.lettuce.core.transaction.RedisTransaction;
import io.lettuce.core.api.async.RedisAsyncCommands;

RedisTransaction tx = connection.sync().multi();
tx.

set("foo","bar").

get("foo");

List<Object> results = tx.exec();
```

#### 管道

```java
import io.lettuce.core.api.async.RedisAsyncCommands;

RedisAsyncCommands<String, String> asyncCommands = connection.async();
asyncCommands.

set("foo","bar");
asyncCommands.

get("foo");
asyncCommands.

flushCommands(); // 触发管道执行
```

## Redis Template

### dependency

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### setup

- config redis connection
- single redis

```properties
spring.data.redis.host=127.0.0.1
spring.data.redis.port=6379
spring.data.redis.password=redis
spring.data.redis.database=0
spring.data.redis.timeout=10000
spring.data.redis.lettuce.pool.max-active=8
spring.data.redis.lettuce.pool.max-wait=-1
spring.data.redis.lettuce.pool.max-idle=8
spring.data.redis.lettuce.pool.min-idle=0
# old version config
#spring.redis.host=127.0.0.1
#spring.redis.port=6379
#spring.redis.password=redis
#spring.redis.database=0
#spring.redis.timeout=10000
```

- redis cluster

```properties
spring.data.redis.password=redis
spring.data.redis.cluster.max-redirects=3
spring.data.redis.cluster.nodes=<node1_ip>:<node1_port>,<node2_ip>:<node2_port>,<node3_ip>:<node3_port>,...
spring.data.redis.lettuce.pool.max-active=8
spring.data.redis.lettuce.pool.max-wait=-1
spring.data.redis.lettuce.pool.max-idle=8
spring.data.redis.lettuce.pool.min-idle=0
spring.data.redis.lettuce.shutdown-timeout=10000
spring.data.redis.lettuce.cluster.refresh.adaptive=true
spring.data.redis.lettuce.cluster.refresh.period=2000
```

- config redis template

```java
package com.li.hellospringbootredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 设置Key的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // 设置Value的序列化方式
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        // store string as redis hash value
        // template.setHashValueSerializer(new GenericToStringSerializer<>(Object.class));
        // store json as redis hash value
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();

        return template;
    }
}

```

### Usage

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 存储字符串
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 获取字符串
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // 存储哈希
    public void setHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    // 获取哈希
    public String getHash(String key, String hashKey) {
        return (String) redisTemplate.opsForHash().get(key, hashKey);
    }

    // 存储列表
    public void setList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // 获取列表
    public String getList(String key, long index) {
        return (String) redisTemplate.opsForList().index(key, index);
    }

    // 存储集合
    public void setSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    // 获取集合
    public Set<String> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // 存储有序集合
    public void setZSet(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    // 获取有序集合
    public Set<String> getZSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }
}

```

```java

@Test
public void test() {
    redisService.setString("redistest_user", "li");
    System.out.println(redisService.getString("redistest_user"));
}
```

### 1. 字符串（String）操作

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 存储字符串
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 获取字符串
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
```

### 2. 哈希（Hash）操作

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 存储哈希
    public void setHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    // 获取哈希
    public String getHash(String key, String hashKey) {
        return (String) redisTemplate.opsForHash().get(key, hashKey);
    }
}
```

### 3. 列表（List）操作

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 存储列表
    public void setList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // 获取列表
    public String getList(String key, long index) {
        return (String) redisTemplate.opsForList().index(key, index);
    }
}
```

### 4. 集合（Set）操作

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 存储集合
    public void setSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    // 获取集合
    public Set<String> getSet(String key) {
        return (Set<String>) redisTemplate.opsForSet().members(key);
    }
}
```

### 5. 有序集合（ZSet）操作

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 存储有序集合
    public void setZSet(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    // 获取有序集合
    public Set<String> getZSet(String key) {
        return (Set<String>) redisTemplate.opsForZSet().range(key, 0, -1);
    }
}
```

### 6. 删除操作

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 删除键
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
```

### 7. 判断键是否存在

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 判断键是否存在
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
```

### 8. 设置过期时间

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 设置键的过期时间
    public void expireKey(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }
}
```

### 9. 获取键的剩余过期时间

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 获取键的剩余过期时间
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }
}
```

### 10. 递增/递减操作

```java
package com.li.hellospringbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 递增操作
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    // 递减操作
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }
}
```

## Auto-refresh cluster view in RedisTemplate and Lettuce

- using RedisTemplate (implement from lettuce) to connect redis <font color="orange">cluster</font>,
  if a master node down, the connection pool <font color="orange">will not refresh automatically</font>.

```properties
spring.data.redis.lettuce.cluster.refresh.adaptive=true
spring.data.redis.lettuce.cluster.refresh.period=2000
```

## Sentinel
- normally sentinel not set password so config password is not required

- method 1: config by properties

```properties
spring.data.redis.sentinel.master=<master_name>
spring.data.redis.sentinel.nodes=<st_node1_ip>:<st_node1_port>,<st_node2_ip>:<st_node2_port>,...
spring.data.redis.sentinel.password=redis
```

- method 2: config by spring config class

```java

@Configuration
public class SentinelConfig {
    @Bean
    public RedisSentinelConfiguration sentinelConfiguration() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("127.0.0.1", 26379)
                .sentinel("127.0.0.1", 26380);
        redisSentinelConfiguration.setPassword("redis");
        return redisSentinelConfiguration;
    }
}
```

# Use MongoDB in Spring Boot
## Use MongoTemplate
### dependency
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

### config
- method 1 (use parameters):
```properties
spring.data.mongodb.host=127.0.0.1
spring.data.mongodb.port=27017
spring.data.mongodb.database=java_test
spring.data.mongodb.username=root
spring.data.mongodb.password=root
spring.data.mongodb.authentication-database=admin
```
- method 2 (use uri):
```properties
spring.data.mongodb.uri=mongodb://root:root@127.0.0.1:27017/java_test?authSource=admin
```

### Usage of MongoTemplate
#### Document
- `@Document(collection="<collectionName>")`: Map Bean class to collection
- `@Id`: Map Bean field to collection's `_id` field
- `@Field("<fieldName>")`: Map Bean field to collection's field
- `@Transient`: Ignore Bean field when mapping to collection


#### 基本 CRUD 操作
1. **插入文档**
  - `insert(Object objectToSave, String collectionName)`
  - `insert(Object objectToSave)`

2. **保存文档**
  - `save(Object objectToSave, String collectionName)`
  - `save(Object objectToSave)`

3. **更新文档**
  - `updateFirst(Query query, Update update, Class<?> entityClass, String collectionName)`
  - `updateFirst(Query query, Update update, Class<?> entityClass)`
  - `updateMulti(Query query, Update update, Class<?> entityClass, String collectionName)`
  - `updateMulti(Query query, Update update, Class<?> entityClass)`

4. **删除文档**
  - `remove(Object object, String collectionName)`
  - `remove(Object object)`
  - `remove(Query query, Class<?> entityClass, String collectionName)`
  - `remove(Query query, Class<?> entityClass)`

#### 查询操作

5. **查找单个文档**
  - `findOne(Query query, Class<T> entityClass, String collectionName)`
  - `findOne(Query query, Class<T> entityClass)`

6. **查找多个文档**
  - `find(Query query, Class<T> entityClass, String collectionName)`
  - `find(Query query, Class<T> entityClass)`

7. **查找所有文档**
  - `findAll(Class<T> entityClass, String collectionName)`
  - `findAll(Class<T> entityClass)`

8. **聚合操作**
  - `aggregate(Aggregation aggregation, Class<T> inputType, Class<O> outputType, String collectionName)`
  - `aggregate(Aggregation aggregation, Class<T> inputType, Class<O> outputType)`

#### 其他操作

9. **执行命令**
  - `executeCommand(String jsonCommand)`
  - `executeCommand(Document command)`

10. **索引操作**
  - `createIndex(Class<?> entityClass, IndexDefinition indexDefinition)`
  - `createIndex(String collectionName, IndexDefinition indexDefinition)`

11. **集合操作**
  - `createCollection(String collectionName)`
  - `dropCollection(String collectionName)`

### 示例代码


#### 插入文档

```java
@Test 
public void test() {
    YourEntity entity = new YourEntity();
    entity.setName("Example");
    mongoTemplate.insert(entity);
}
```


#### 保存文档

```java
@Test
public void test(){
    YourEntity entity = new YourEntity();
    entity.setName("Example");
    mongoTemplate.save(entity);
}
```


#### 更新文档

```java
@Test
public void test() {
    Query query = new Query(Criteria.where("name").is("Example"));
    Update update = new Update().set("name", "Updated Example");
    mongoTemplate.updateFirst(query, update, YourEntity.class);
}
```


#### 删除文档

```java
@Test
public void test(){
    Query query = new Query(Criteria.where("name").is("Updated Example"));
    mongoTemplate.remove(query, YourEntity.class);
}
```


#### 查找文档

```java
@Test
public void test(){
    Query query = new Query(Criteria.where("name").is("Example"));
    YourEntity entity = mongoTemplate.findOne(query, YourEntity.class);
}
```


#### 聚合操作

```java
@Test
public void test(){
    Aggregation aggregation = Aggregation.newAggregation(
        Aggregation.match(Criteria.where("status").is("A")),
        Aggregation.group("category").count().as("total")
    );
    AggregationResults<AggregationResult> results = mongoTemplate.aggregate(aggregation, "yourCollectionName", AggregationResult.class);
}
```

### 操作实例
```java
package com.li.hellospringbootredis;

import com.li.hellospringbootredis.pojo.TestCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoDBTest {
  @Autowired
  MongoTemplate mongoTemplate;

  @Test
  public void testMongoCreateCol() {
    if (mongoTemplate.collectionExists("test_collection")) {
      mongoTemplate.dropCollection("test_collection");
    }
    mongoTemplate.createCollection("test_collection");
  }

  @Test
  public void testMongoIndex() {
      // "name" in Index("name") means the field name in mongodb collection
//        Index index = new Index("name", Sort.Direction.ASC);
    Index index = new Index("name", Sort.Direction.DESC);
    mongoTemplate.indexOps("test_collection").ensureIndex(index);
  }

  @Test
  public void testMongoInsert() {
    mongoTemplate.insert(new TestCollection("1", "test1", ""));
    mongoTemplate.insert(new TestCollection("2", "test2", ""));
    mongoTemplate.insert(new TestCollection("3", "test3", ""));
    mongoTemplate.insert(new TestCollection("4", "test4", ""));
    mongoTemplate.insert(new TestCollection("5", "test5", ""));
    mongoTemplate.insert(new TestCollection("6", "test6", ""));
    mongoTemplate.insert(new TestCollection("7", "test7", ""));
    mongoTemplate.insert(new TestCollection("8", "test8", ""));
  }

  @Test
  public void testMongoQuery() {
    System.out.println(mongoTemplate.findOne(new Query(), TestCollection.class));

    // 查询单个 TestCollection 对象，name 为 "test1"
    Query query = new Query(Criteria.where("name").is("test1"));
    TestCollection testCollection = mongoTemplate.findOne(query, TestCollection.class);
    System.out.println(testCollection);

    System.out.println(mongoTemplate.findAll(TestCollection.class));

  }

  @Test
  public void testMongoUpdate() {
    Query query = new Query(Criteria.where("name").is("test1"));
    System.out.println(mongoTemplate.findOne(query, TestCollection.class));
    Update update = new Update().set("name", "Updata test1");
    System.out.println(mongoTemplate.findOne(query, TestCollection.class));
    mongoTemplate.updateFirst(query, update, TestCollection.class);
    System.out.println(mongoTemplate.findOne(
            new Query(
                    Criteria.where("id")
                            .is("1")
            ),
            TestCollection.class)
    );
  }

  @Test
  public void testMongoDelete() {
    Query query = new Query(Criteria.where("name").is("Updata test1"));
    mongoTemplate.remove(query, TestCollection.class);
    System.out.println(mongoTemplate.findAll(TestCollection.class));
//        mongoTemplate.dropCollection("test_collection");
    mongoTemplate.remove(new Query(), TestCollection.class);
    System.out.println(mongoTemplate.findAll(TestCollection.class));
  }

}

```