# JDBC (Java Database Connectivity)
JDBC provides interface
---
- [JDBC (Java Database Connectivity)](#jdbc-java-database-connectivity)
  - [JDBC provides interface](#jdbc-provides-interface)
  - [Use JDBC in project](#use-jdbc-in-project)
    - [Environment prepare](#environment-prepare)
    - [JDBC usage](#jdbc-usage)

## Use JDBC in project
### Environment prepare
1. prepare sql
2. get connector driver jar package or add maven
3. if use jar driver pack, add if to project lib directory
```xml
<!-- pom.xml -->
<!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.0.0</version>
</dependency>
```
### JDBC usage
1. regist jdbc driver using reflect
2. get connection
3. get statement
4. exec sql
5. get result from ResultSet
6. release resource (ResultSet / Statement / Connection).close()
```java
try {
    // 1. regist jdbc
    Class jdbc = Class.forName("com.mysql.cj.jdbc.Driver");

    // 2. get connection
    String url = "jdbc:mysql://localhost:13306/webtest";
    String username = "root";
    String password = "lijunjie";
    try (Connection conn = DriverManager.getConnection(url, username, password)) {

        // 3. get statement
        try (Statement stmt = conn.createStatement()) {

            // 4. exec sql
            String sql = "select * from t_emp";
            try (ResultSet result = stmt.executeQuery(sql)) {
                // 5. get result from ResultSet
                while (result.next()) {
                    int emp_id = result.getInt("emp_id");
                    String emp_name = result.getString("emp_name");
                    int emp_age = result.getInt("emp_age");
                    double emp_salary = result.getDouble("emp_salary");
                    resp.getWriter().write("************************\n");
                    resp.getWriter().write(String.format("id: %d\nname:%s\nage:%d\nsalary:%f\n", emp_id, emp_name, emp_age, emp_salary));
                }
            }
        }
    }
    // 6. release resource (ResultSet / Statement / Connection).close()
} catch (Exception e) {
    e.printStackTrace();
}
```

