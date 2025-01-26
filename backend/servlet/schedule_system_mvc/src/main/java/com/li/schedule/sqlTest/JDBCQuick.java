package com.li.schedule.sqlTest;

import com.mysql.cj.jdbc.Driver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "jdbcquick", value = "/jdbcquick")
public class JDBCQuick extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                            resp.getWriter().write(String.format("id: %d\nname:%s\nage:%d\nsalary:%.3f\n", emp_id, emp_name, emp_age, emp_salary));
                        }
                    }
                }
            }
            // 6. release resource (ResultSet / Statement / Connection).close()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
