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

- 在 `application.properties` 或 `application.yaml` 中添加以下配置以启用更多的调试信息：

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

