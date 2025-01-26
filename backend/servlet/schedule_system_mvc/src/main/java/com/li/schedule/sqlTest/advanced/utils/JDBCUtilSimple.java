package com.li.schedule.sqlTest.advanced.utils;

import com.li.schedule.sqlTest.advanced.pojo.Employee;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * JDBC Util class
 * 1. connection pool maintenance
 * 2. get connection
 * 3. recycle connection
 */
public class JDBCUtilSimple {
    private static volatile DataSource ds;

    private static void genDataSource() {
        try {
            Properties prop = new Properties();
            InputStream in = JDBCUtilSimple.class.getClassLoader().getResourceAsStream("db_h.properties");
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

    public static void release(Connection conn) throws SQLException {
        conn.close();
    }

    @Test
    public void testGetConnection() throws Exception {

        Date start = new Date();

        List<Thread> threads = new LinkedList<>();
        for (int i = 1; i < 12; i++) {
            final String emp_id = String.valueOf(i);
            threads.add(new Thread(() -> {
                System.out.println(Thread.currentThread().getId());
                try (Connection conn = JDBCUtilSimple.getConnection()) {
                    System.out.println(JDBCUtilSimple.getDataSource());
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
}
