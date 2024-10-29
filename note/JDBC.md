# JDBC (Java Database Connectivity)

- [JDBC (Java Database Connectivity)](#jdbc-java-database-connectivity)
  - [JDBC provides interaction](#jdbc-provides-interaction)
  - [Use JDBC in project](#use-jdbc-in-project)
    - [Environment prepare](#environment-prepare)
    - [JDBC usage](#jdbc-usage)
    - [Use PreparedStatement to avoid injection](#use-preparedstatement-to-avoid-injection)
    - [ResultSet only store result of query](#resultset-only-store-result-of-query)
    - [Basic operations with JDBC](#basic-operations-with-jdbc)
  - [Efficienct JDBC](#efficienct-jdbc)
    - [Entity class and ORM](#entity-class-and-orm)
    - [Statement options and return generated keys](#statement-options-and-return-generated-keys)
    - [Insert large number of rows](#insert-large-number-of-rows)
  - [Connection Pool](#connection-pool)
    - [Druid](#druid)
        - [Init DataSource](#init-datasource)
          - [method 1: use set method](#method-1-use-set-method)
          - [method 2: via db.properties](#method-2-via-dbproperties)
      - [usage of DataSource](#usage-of-datasource)
    - [Hikari](#hikari)
        - [Init DataSource](#init-datasource-1)
          - [method 1: use set method](#method-1-use-set-method-1)
          - [method 1: use set method](#method-1-use-set-method-2)
  - [Advanced JDBC Utils](#advanced-jdbc-utils)
    - [A simple JDBC Util class](#a-simple-jdbc-util-class)
## JDBC provides interaction
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

### ResultSet only store result of query

### Basic operations with JDBC
- query one value: use `AS` in sql to rename to 'name' and call `ResultSet.getInt("name")` to get this value.
- query row(s): 'ResultSet.next()' must be called before using `getXxx("xx")` to get value, each call of 'ResultSet.next()' updates date to next row.
- insert / update / delete: PreparedStatement.executeUpdate() will directly return a `int` value means **affect rows**.
```java
import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class JDBCOperation {
    @Test
    // query one value
    public void testQuerySingleRowAndCol() throws Exception {
        DriverManager.registerDriver(new Driver());
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:13306/webtest", "root", "lijunjie")) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS `count` FROM `t_emp`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt("count");
                        System.out.println("count: " + count);
                    }
                }
            }
        }
    }

    @Test
    // query one row
    public void testQuerySingleRow() throws Exception {
        DriverManager.registerDriver(new Driver());
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:13306/webtest", "root", "lijunjie")) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM `t_emp` WHERE `emp_salary` < ?")) {
                String max = "500";
                ps.setString(1, max);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String emp_name = rs.getString("emp_name");
                        double emp_salary = rs.getDouble("emp_salary");
                        System.out.println("emp_name: " + emp_name + ", emp_salary: " + emp_salary);
                    }
                }
            }
        }
    }

    @Test
    // query many row
    public void testQueryManyRow() throws Exception {
        DriverManager.registerDriver(new Driver());
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:13306/webtest", "root", "lijunjie")) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM `t_emp` WHERE `emp_age` > ?")) {
                String minAge = "20";
                ps.setString(1, minAge);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs != null && rs.next()) {
                        int emp_id = rs.getInt("emp_id");
                        String emp_name = rs.getString("emp_name");
                        int emp_age = rs.getInt("emp_age");
                        double emp_salary = rs.getDouble("emp_salary");
                        System.out.println("************************");
                        System.out.println(String.format("id: %d\nname:%s\nage:%d\nsalary:%.3f\n", emp_id, emp_name, emp_age, emp_salary));
                    }
                }
            }
        }
    }

    @Test
    // insert a row
    public void testInsertSingleRow() throws Exception {
        DriverManager.registerDriver(new Driver());
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:13306/webtest", "root", "lijunjie")) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO `t_emp` (`emp_name`, `emp_age`, emp_salary) VALUES (?, ?, ?)")) {
                String emp_name = "wu";
                int emp_age = 52;
                double emp_salary = 1000;
                ps.setString(1, emp_name);
                ps.setInt(2, emp_age);
                ps.setDouble(3, emp_salary);
                int result = ps.executeUpdate();
                System.out.println("affect rows: " + result);
            }
        }
    }

    @Test
    // update data
    public void testUpdate() throws Exception {
        DriverManager.registerDriver(new Driver());
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:13306/webtest", "root", "lijunjie")) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE `t_emp` SET `emp_salary` = `emp_salary`+ ? WHERE `emp_id` = ?")) {
                double add_salary = 10;
                int emp_id = 5;
                ps.setDouble(1, add_salary);
                ps.setInt(2, emp_id);
                int result = ps.executeUpdate();
                System.out.println("affect rows: " + result);
            }
        }
    }

    @Test
    // delete from data
    public void testDelete() throws Exception {
        DriverManager.registerDriver(new Driver());
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:13306/webtest", "root", "lijunjie")) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM `t_emp` WHERE `emp_id` = ?")) {
                int emp_id = 6;
                ps.setDouble(1, emp_id);
                int result = ps.executeUpdate();
                System.out.println("affect rows: " + result);
            }
        }
    }
}
```
## Efficienct JDBC
### Entity class and ORM
One entity class for one database table:
```java
// Employee.java
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    private Integer empId;
    private String empName;
    private Integer empAge;
    private Double empSalary;
}

// JDBCORMTest.java
public class JDBCORMTest {
    @Test
    public void getAll() throws Exception {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:13306/webtest", "root", "lijunjie")) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT `emp_id`, `emp_name`, `emp_age`, `emp_salary` FROM `t_emp`")) {
                try (ResultSet rs = ps.executeQuery()) {
                    List<Employee> employees = new LinkedList<>();
                    while (rs != null && rs.next()) {
                        Employee emp = new Employee();
                        int emp_id = rs.getInt("emp_id");
                        String emp_name = rs.getString("emp_name");
                        int emp_age = rs.getInt("emp_age");
                        double emp_salary = rs.getDouble("emp_salary");
                        emp.setEmpId(emp_id);
                        emp.setEmpName(emp_name);
                        emp.setEmpAge(emp_age);
                        emp.setEmpSalary(emp_salary);
                        employees.add(emp);
                    }
                    for (Employee emp : employees) {
                        System.out.println(emp);
                    }
                }
            }
        }
    }
}
```
### Statement options and return generated keys
```java
/**
 * The constant indicating that the current {@code ResultSet} object
 * should be closed when calling {@code getMoreResults}.
 *
 * @since 1.4
 */
int CLOSE_CURRENT_RESULT = 1;

/**
* The constant indicating that the current {@code ResultSet} object
* should not be closed when calling {@code getMoreResults}.
*
* @since 1.4
*/
int KEEP_CURRENT_RESULT = 2;

/**
* The constant indicating that all {@code ResultSet} objects that
* have previously been kept open should be closed when calling
* {@code getMoreResults}.
*
* @since 1.4
*/
int CLOSE_ALL_RESULTS = 3;

/**
* The constant indicating that a batch statement executed successfully
* but that no count of the number of rows it affected is available.
*
* @since 1.4
*/
int SUCCESS_NO_INFO = -2;

/**
* The constant indicating that an error occurred while executing a
* batch statement.
*
* @since 1.4
*/
int EXECUTE_FAILED = -3;

/**
* The constant indicating that generated keys should be made
* available for retrieval.
*
* @since 1.4
*/
int RETURN_GENERATED_KEYS = 1;

/**
* The constant indicating that generated keys should not be made
* available for retrieval.
*
* @since 1.4
*/
int NO_GENERATED_KEYS = 2;
```
- `RETURN_GENERATED_KEYS`: return auto increased primary key after sucessfully inserting, call `PreparedStatement.getGeneratedKeys()` to get it.
```java
@Test
public void primaryKeyCallback() throws Exception {
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:13306/webtest", "root", "lijunjie")) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO `t_emp` (`emp_name`, `emp_age`, emp_salary) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            Employee emp = new Employee(null, "zhen", 35, 555.555);
            ps.setString(1, emp.getEmpName());
            ps.setInt(2, emp.getEmpAge());
            ps.setDouble(3, emp.getEmpSalary());
            int af = ps.executeUpdate();
            if (af > 0) {
                System.out.println("success");
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int empId = rs.getInt(1);
                        emp.setEmpId(empId);
                        System.out.println(emp);
                    }
                }
            } else
                System.out.println("not success yet");
        }
    }
}
```

### Insert large number of rows
- add `rewriteBatchedStatements=true` parameter to jdbc url
- sql should not end with `';'`, and should use `VALUES` instead of `VALUE`
- call `PreparedStatement.addBatch()` for each row to add one insert
- call `PreparedStatement.executeBatch()` after all row has been added
- `PreparedStatement.executeBatch()` returns an int array, each int means result of on row, for `Statement.SUCCESS_NO_INFO` is `-2`, so the int `-2` means successful
```java
@Test
public void testInsertMany() throws Exception{
    DriverManager.registerDriver(new Driver());
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:13306/webtest?rewriteBatchedStatements=true", "root", "lijunjie")) {
        List<Employee> employees = new LinkedList<>();
        employees.add(new Employee(null, "aaa", 20, 200.0));
        employees.add(new Employee(null, "bbb", 20, 200.0));
        employees.add(new Employee(null, "ccc", 20, 200.0));
        employees.add(new Employee(null, "ddd", 20, 200.0));
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO `t_emp` (`emp_name`, `emp_age`, emp_salary) VALUES (?, ?, ?)")) {
            for (Employee employee : employees) {
                ps.setString(1, employee.getEmpName());
                ps.setInt(2, employee.getEmpAge());
                ps.setDouble(3, employee.getEmpSalary());
                ps.addBatch();
            }
            int[] result = ps.executeBatch();
            for (int i = 0; i < result.length; i++) {
                System.out.println("affect rows: " + result[i]);
            }
            System.out.println("affect rows: " + result.length);
        }
    }
}
```
## Connection Pool
### Druid
1. create `db.properties` in resource directory and state `driverClassName/url/username/password/...`
2. create and init DruidDataSource
3. get Connection from DruidDataSource
4. use Connection as in JDBC
5. recycle Connection with `close()`

##### Init DataSource
###### method 1: use set method
```java
// create DruidDataSource
DruidDataSource dds = new DruidDataSource();
// init DruidDataSource
dds.setDriverClassName("com.mysql.cj.jdbc.Driver");
dds.setUsername("root");
dds.setPassword("lijunjie");
dds.setUrl("jdbc:mysql://127.0.0.1:13306/webtest");
dds.setInitialSize(5);
dds.setMaxActive(10);
```
###### method 2: via db.properties
- `DruidDataSourceFactory.createDataSource` only return a `DataSource` object instead of `DruidDataSource`
```properties
# db.properties
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://127.0.0.1:13306/webtest
username=root
password=lijunjie
initialSize=5
maxAcitve=10
```
```java
// get properties context
Properties druidProperties = new Properties();
InputStream propertiesInputStream = this.getClass().getClassLoader().getResourceAsStream("db.properties");
druidProperties.load(propertiesStream);

// create DataSource by DruidDataSourceFactory
DataSource dds = DruidDataSourceFactory.createDataSource(druidProperties);
```

#### usage of DataSource
```java
Date start = new Date();
List<Thread> threads = new LinkedList<>();
for (int i = 1; i < 12; i++) {
    final String emp_id = String.valueOf(i);
    threads.add(new Thread(() -> {
        System.out.println(Thread.currentThread().getId());
        try (Connection conn = dds.getConnection()) {
            System.out.println(conn);
            Thread.sleep(2000);
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM `t_emp` WHERE `emp_id`=?")) {
                ps.setString(1, emp_id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        System.out.println(
                                new Employee(
                                    rs.getInt("emp_id"),
                                    rs.getString("emp_name"),
                                    rs.getInt("emp_age"),
                                    rs.getDouble("emp_salary")
                                )
                        );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }));
}
for (Thread thread : threads)
    thread.start();

for (Thread thread : threads)
    thread.join();

System.out.println(String.format("use time: %dms", System.currentTimeMillis() - start.getTime()));
```

### Hikari
1. create `db_h.properties` in resource directory and state `driverClassName/jdbcUrl/username/password/...`
2. create and initDruidDataSource
3. get Connection from DruidDataSource
4. use Connection as in JDBC
5. recycle Connection with `close()`

##### Init DataSource
###### method 1: use set method
```java
// create hikari datasource and configure
HikariDataSource hds = new HikariDataSource();
// init HikariDataSource
hds.setDriverClassName("com.mysql.cj.jdbc.Driver");
hds.setJdbcUrl("jdbc:mysql://127.0.0.1:13306/webtest");
hds.setUsername("root");
hds.setPassword("lijunjie");
hds.setMinimumIdle(5);
hds.setMaximumPoolSize(10);
```
###### method 1: use set method
1. create and read properties file
2. use properties file to create `HikariConfig`
3. use `HikariConfig` to create `HikariDataSource`
```properties
# db_h.properties
driverClassName=com.mysql.cj.jdbc.Driver
jdbcUrl=jdbc:mysql://127.0.0.1:13306/webtest
username=root
password=lijunjie
minimumIdle=5
maximumPoolSize=10
```
```java
// create properties set
Properties hikariProperties = new Properties();
// read properties file
InputStream propertiesInputStream = this.getClass().getClassLoader().getResourceAsStream("db_h.properties");
hikariProperties.load(propertiesInputStream);
// create HikariConfig object with properties set
HikariConfig hikariConfig = new HikariConfig(hikariProperties);
// create HikariDataSource with HikariConfig
HikariDataSource hds = new HikariDataSource(hikariConfig);
```
## Advanced JDBC Utils
### A simple JDBC Util class
```java
/**
 * JDBC Util class
 * 1. connection pool maintenance
 * 2. get connection
 * 3. recycle connection
 */
public class JDBCUtil {
    private static DataSource ds;

    private static void genDataSource() {
        try {
            Properties prop = new Properties();
            InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("db_h.properties");
            prop.load(in);
            HikariConfig config = new HikariConfig(prop);
            ds = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DataSource getDataSource() {
        if (ds == null) {
            System.out.println("construct DataSource");
            genDataSource();
        }
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    // ignore if try is used to get Connection
    public static void release(Connection conn) throws SQLException {
        conn.close();
    }
}

@Test
public void testGetConnection() throws Exception {

    Date start = new Date();

    List<Thread> threads = new LinkedList<>();
    for (int i = 1; i < 12; i++) {
        final String emp_id = String.valueOf(i);
        threads.add(new Thread(() -> {
            System.out.println(Thread.currentThread().getId());
            try (Connection conn = JDBCUtil.getConnection()) {
                System.out.println(JDBCUtil.getDataSource());
                System.out.println(conn);
                Thread.sleep(2000);
                try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM `t_emp` WHERE `emp_id`=?")) {
                    ps.setString(1, emp_id);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next())
                            System.out.println(
                                    new Employee(
                                            rs.getInt("emp_id"),
                                            rs.getString("emp_name"),
                                            rs.getInt("emp_age"),
                                            rs.getDouble("emp_salary")
                                    )
                            );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }
    for (Thread thread : threads)
        thread.start();

    for (Thread thread : threads)
        thread.join();

    System.out.println(String.format("use time: %dms", System.currentTimeMillis() - start.getTime()));
}
```
