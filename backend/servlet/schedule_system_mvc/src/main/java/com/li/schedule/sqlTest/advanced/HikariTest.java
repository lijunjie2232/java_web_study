package com.li.schedule.sqlTest.advanced;

import com.li.schedule.sqlTest.advanced.pojo.Employee;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class HikariTest {
    @Test
    public void hardTest() throws Exception {
        // create hikari datasource and configure
        HikariDataSource hds = new HikariDataSource();
        hds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hds.setJdbcUrl("jdbc:mysql://127.0.0.1:13306/webtest");
        hds.setUsername("root");
        hds.setPassword("lijunjie");
        hds.setMinimumIdle(5);
        hds.setMaximumPoolSize(10);

        System.out.println(hds);

        List<Thread> threads = new LinkedList<>();
        for (int i = 1; i < 12; i++) {
            final String emp_id = String.valueOf(i);
            threads.add(new Thread(() -> {
                System.out.println(Thread.currentThread().getId());
                try (Connection conn = hds.getConnection()) {
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

    }

    @Test
    public void softTest() throws Exception {
        // create properties set
        Properties hikariProperties = new Properties();
        // read properties file
        InputStream propertiesInputStream = this.getClass().getClassLoader().getResourceAsStream("db_h.properties");
        hikariProperties.load(propertiesInputStream);
        // create HikariConfig object with properties set
        HikariConfig hikariConfig = new HikariConfig(hikariProperties);
        // create HikariDataSource with HikariConfig
        HikariDataSource hds = new HikariDataSource(hikariConfig);

        System.out.println(hds);

        Date start = new Date();

        List<Thread> threads = new LinkedList<>();
        for (int i = 1; i < 12; i++) {
            final String emp_id = String.valueOf(i);
            threads.add(new Thread(() -> {
                System.out.println(Thread.currentThread().getId());
                try (Connection conn = hds.getConnection()) {
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
