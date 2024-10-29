package com.li.schedule.sqlTest.advanced;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.li.schedule.sqlTest.advanced.pojo.Employee;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class DruidTest {

    @Test
    public void hardDruid() throws Exception {
        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dds.setUsername("root");
        dds.setPassword("lijunjie");
        dds.setUrl("jdbc:mysql://127.0.0.1:13306/webtest");
        dds.setInitialSize(5);
        dds.setMaxActive(10);

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

    }

    @Test
    public void softDruid() throws Exception {
        // get properties context
        Properties druidProperties = new Properties();
        InputStream propertiesStream = DruidTest.class.getClassLoader().getResourceAsStream("db.properties");
        druidProperties.load(propertiesStream);

        // create DruidDataSource by DruidDataSourceFactory
        DataSource dds = DruidDataSourceFactory.createDataSource(druidProperties);
        System.out.println(dds);

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

    }
}
