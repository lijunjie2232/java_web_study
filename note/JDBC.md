# JDBC (Java Database Connectivity)
JDBC provides interface
---
- [JDBC (Java Database Connectivity)](#jdbc-java-database-connectivity)
  - [JDBC provides interface](#jdbc-provides-interface)
  - [Use JDBC in project](#use-jdbc-in-project)
    - [Environment prepare](#environment-prepare)
    - [JDBC usage](#jdbc-usage)
    - [Use PreparedStatement to avoid injection](#use-preparedstatement-to-avoid-injection)

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
    // method 1
    // Class jdbc = Class.forName("com.mysql.cj.jdbc.Driver");
    // method 2
    DriverManager.registerDriver(new Driver());

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

- jdk 6+ could ignore regist driver
- Connection: connect to database
- Statement: excute sql, interact with database, however, directly pass sql into statement is unsafe and easily get injected. For example, `sql = "SELECT * FROM t_emp WHERE emp_id = '" + name + "'";`, while `name="' OR TRUE OR '"`, the `sql` is `SELECT * FROM t_emp WHERE emp_id = '' OR TRUE OR ''`, so the condition is always true.
### Use PreparedStatement to avoid injection
- sql will be compile into PreparedStatement instance, so sql is needed while constructing PreparedStatement, unknown field should placed with '?'
- use `stmt.setString(int, String)` to set `String` information into the `int`th '?', **`int` starts at 1**!!!
```java
// String id = "' OR TRUE OR '";
String id = "1";
String sql = "SELECT * FROM t_emp WHERE emp_id = ?";
// 3. get PreparedStatement from Connection
try(PreparedStatement stmt = conn.prepareStatement(sql)){
    stmt.setString(1, id);
    System.out.println(stmt);
    // 4. exec sql
    try (ResultSet result = stmt.executeQuery()) {
        // 5. get result from ResultSet
        while (result.next()) {
            int emp_id = result.getInt("emp_id");
            String emp_name = result.getString("emp_name");
            int emp_age = result.getInt("emp_age");
            double emp_salary = result.getDouble("emp_salary");
            resp.getWriter().write("************************\n");
            resp.getWriter().write(String.format("id: %d\nname:%s\nage:%d\nsalary:%.3f\n", emp_id, emp_name, emp_age, emp_salary));
        }
    }
} catch (Exception e){}
```

