# Spring Boot auto configuration

## example: change default data source

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

## 启用调试日志

- 在 `application.properties` 或 `application.yaml` 中添加以下配置以启用更多的调试信息：

```properties
logging.level.org.springframework.boot.autoconfigure=DEBUG
```

