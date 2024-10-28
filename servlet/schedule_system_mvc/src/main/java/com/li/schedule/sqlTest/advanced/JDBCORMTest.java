package com.li.schedule.sqlTest.advanced;

import com.li.schedule.sqlTest.advanced.pojo.Employee;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.*;

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
