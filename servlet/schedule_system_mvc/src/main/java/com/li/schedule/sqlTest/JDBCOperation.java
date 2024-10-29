package com.li.schedule.sqlTest;

import com.li.schedule.sqlTest.advanced.pojo.Employee;
import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

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
