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
          - [method 2: via db.properties](#method-2-via-dbproperties-1)
  - [Advanced JDBC Utils](#advanced-jdbc-utils)
    - [A simple JDBC Util class](#a-simple-jdbc-util-class)
    - [Use ThreadLocal to bind a connection to a thread](#use-threadlocal-to-bind-a-connection-to-a-thread)
    - [DAO and BaseDAO](#dao-and-basedao)
      - [A simple BaseDAO](#a-simple-basedao)
    - [Transaction](#transaction)
      - [Isolation level:](#isolation-level)
      - [JDBC transaction](#jdbc-transaction)
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
###### method 2: via db.properties
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
### Use ThreadLocal to bind a connection to a thread
- state a `TreadLocal<Connection>` member to class
- use `ThreadLocal.set(Connection)` to bind a connection at first time to get connection
- `ThreadLocal.get()` to get the Connection
- `ThreadLocal.remove()` to remove the Connectino after close is
```java
/**
 * JDBC Util class v2
 * 1. connection pool maintenance
 * 2. get connection
 * 3. recycle connection
 * 4. bind one connectino to one thread
 */
public class JDBCUtilWithThreadLocal {
    private static DataSource ds;
    private static ThreadLocal<Connection> threadConn = new ThreadLocal<>();

    private static void genDataSource() {
        try {
            Properties prop = new Properties();
            InputStream in = JDBCUtilWithThreadLocal.class.getClassLoader().getResourceAsStream("db_h.properties");
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
        Connection conn = threadConn.get();
        if (conn == null) {
            conn = JDBCUtilWithThreadLocal.getDataSource().getConnection();
            threadConn.set(conn);
        }
        return conn;
    }

    public static void release() throws SQLException {
        Connection conn = threadConn.get();
        if (conn != null) {
            threadConn.remove();
            conn.close();
        }
    }

    @Test
    public void testGetConnection() throws Exception {

        Date start = new Date();

        List<Thread> threads = new LinkedList<>();
        for (int i = 1; i < 5; i++) {
            final String emp_id = String.valueOf(i);
            threads.add(new Thread(() -> {
                System.out.println(Thread.currentThread().getId());
                System.out.println(JDBCUtilWithThreadLocal.getDataSource());
                for (int j = 0; j < 10; j ++) {
                    try {
                        Connection conn = JDBCUtilWithThreadLocal.getConnection();
                        System.out.println(String.format("[threadId]: %d, [Connection]: %s", Thread.currentThread().getId(), conn));
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
                }
            }));
        }
        for (Thread thread : threads)
            thread.start();

        for (Thread thread : threads)
            thread.join();

        System.out.println(String.format("use time: %dms", System.currentTimeMillis() - start.getTime()));

        JDBCUtilWithThreadLocal.release();
    }
}
```
### DAO and BaseDAO
- DAO only focus on Database operations
- BaseDAO encapsulats much duplicate code such as general query or insert and so on, and is to be extended by each DAO class.
#### A simple BaseDAO
```java
public class BaseDao {
    /**
     * general update one row
     *
     * @param sql:    sql setence
     * @param params: variable in sql
     * @return affect row
     * @throws Exception
     */
    public int excuetUpdate(String sql, Object... params) throws Exception {
        Connection conn = JDBCUtilWithThreadLocal.getConnection();
        int row = 0;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            row = ps.executeUpdate();
        }
        JDBCUtilWithThreadLocal.release();
        return row;
    }

    /**
     * general query
     *
     * @param clazz:  DAO object class
     * @param sql:    sql setence
     * @param params: variable in sql
     * @return query result
     * @throws
     */
    public <T> List<T> executeQuery(Class<T> clazz, String sql, Object... params) throws Exception {
        Connection conn = JDBCUtilWithThreadLocal.getConnection();
        List<T> clazzList = new LinkedList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    T t = clazz.getDeclaredConstructor().newInstance();
                    for (int i = 1; i <= columnCount; i++) {
                        String fieldName = rsmd.getColumnLabel(i);
                        Field field = clazz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.set(t, rs.getObject(i));
                        field.setAccessible(false);
                    }
                    clazzList.add(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JDBCUtilWithThreadLocal.release();
        return clazzList;
    }

    /**
     * general only first row of query
     *
     * @param clazz:  DAO object class
     * @param sql:    sql setence
     * @param params: variable in sql
     * @return query result
     * @throws
     */
    public <T> T executeQuery4OneRow(Class<T> clazz, String sql, Object... params) throws Exception {
        List<T> result = this.executeQuery(clazz, sql, params);
        if (result.size() > 0) {
            return result.get(0);
        } else return null;
    }
}
```
```java
public class SysUserDaoImpl extends BaseDao implements SysUserDao {

    @Override
    public List<SysUser> selectAll() {
        String sql = "SELECT * FROM `sys_user`";
        List<SysUser> users = null;
        try {
            users = this.executeQuery(SysUser.class, sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public SysUser selectByUid(Integer uid) {
        String sql = "SELECT * FROM `sys_user` WHERE `uid`=?";
        SysUser user = null;
        try {
            user = this.executeQuery4OneRow(SysUser.class, sql, uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public SysUser addUser(SysUser user) {
        String sql = "INSERT INTO `sys_user`(`username`, `password`) VALUES (?,?)";
        try {
            user.setUid(this.executeInsertWithGenKey(sql, user.getUsername(), user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int updateUser(SysUser user) {
        String sql = "UPDATE `sys_user` SET `username`=?";
        if (user.getPassword() != null && !user.getPassword().equals(""))
            sql += ", `password`=?";
        sql += " WHERE `uid`=?";
        try {
            if (user.getPassword() != null && !user.getPassword().equals(""))
                return this.executeUpdate(sql, user.getUsername(), user.getPassword(), user.getUid());
            else
                return this.executeUpdate(sql, user.getUsername(), user.getUid());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteByUid(Integer uid) {
        String sql = "DELETE FROm `sys_user` WHERE `uid`=?";
        try {
            return this.executeUpdate(sql, uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
```
### Transaction
- type sql cmd `SHOW VARIABLES LIKE 'autocommit'` to check if transaction commit automaticly feature is enabled
- type sql cmd `SET autocommit = FALSE` or `SET autocommit = 0` to disable transaction commit automaticly feature
- sql transaction oprations:
  - transactin features:
    - Atomicity: 事务的整个过程如原子操作一样，最终要么全部成功，或者全部失败，这个原子性是从最终结果来看的，从最终结果来看这个过程是不可分割的。
    - Aconsistency: 一个事务必须使数据库从一个一致性状态变换到另一个一致性状态。
    - Isolation: 一个事务的执行不能被其他事务干扰。
    - Durability: 一个事务一旦提交，他对数据库中数据的改变就应该是永久性的。
  - `COMMIT` commit manually above sql operations
  - `ROLLBACK` discommit all sql operations above
#### Isolation level
- 读未提交：read uncommitted
- 读已提交：read committed
- 可重复读：repeatable read
- 串行化：serializable, 会让并发的事务串行执行（多个事务之间读写、写读、写写会产生互斥，效果就是串行执行，多个事务之间的读读不会产生互斥）。
- check isolation level:
```sql
show variables like 'transaction_isolation';
```
- modify isolation level in my.init setting `transaction-isolation=[isolation_level]`

| 隔离级别         | 脏读可能性 | 不可重复读可能性 | 幻读可能性 |
| ---------------- | ---------- | ---------------- | ---------- |
| READ-UNCOMMITTED | 有         | 有               | 有         |
| READ-COMMITTED   | 无         | 有               | 有         |
| REPEATABLE-READ  | 无         | 无               | 有         |
| SERIALIZABLE     | 无         | 无               | 无         |
- 脏读 (Dirty Read): 一个事务会读到另一个事务更新后但未提交的数据
- 不可重复读: 在同一事务中，多次读取同一数据返回的结果有所不同(其他事务修改导致)
- 幻读： 由于可重复读，其他事务实时提交的更改无法获取，导致本事务接下来的更改与其他事务刚刚提交的更改冲突

#### JDBC transaction
- ThreadLocal must be used to get Connection object
- key code piece
```java
// JDBCUtilWithThreadLocal.java
// change release to fix connection auto commit
public static void release() throws Exception {
    Connection conn = threadConn.get();
    if (conn != null && conn.getAutoCommit()) {
        threadConn.remove();
        conn.close();
    } else {
        System.out.println("change connection autocommit to true before release");
    }
}
```
```java
@Test
public void JDBCTransactionTest() throws SQLException {
    Connection conn = null;
    try {
        AccountDaoImpl accountDaoImpl = new AccountDaoImpl();
        Account ac_a = accountDaoImpl.selectById(1);
        Account ac_b = accountDaoImpl.selectById(2);
        System.out.println(ac_a);
        System.out.println(ac_b);
        conn = JDBCUtilWithThreadLocal.getConnection();
        System.out.println(conn);
        // disable auto commit
        conn.setAutoCommit(false);
        accountDaoImpl.balanceChange(ac_b, 10000.0);
        System.out.println(accountDaoImpl.selectById(ac_b.getId()));
        accountDaoImpl.balanceChange(ac_a, -10000.0);
        System.out.println(accountDaoImpl.selectById(ac_a.getId()));
        conn.commit();
    } catch (Exception e) {
        e.printStackTrace();
        if (conn != null) {
            System.out.println("rollback ...");
            // rollback transaction if get error
            conn.rollback();
        }
    } finally {
        if (conn != null) {
            System.out.println("do finally routine ...");
            // enable auto commit
            conn.setAutoCommit(true);
            JDBCUtilWithThreadLocal.release();
        }
    }
}
```