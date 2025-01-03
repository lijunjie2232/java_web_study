package com.li.hellospringrestful.dao.impl;

import com.li.hellospringrestful.bean.Employee;
import com.li.hellospringrestful.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM `employee` WHERE `id` = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Employee>(Employee.class), id);
    }

    @Override
    public void addEmployee(Employee employee) {
        
    }

    @Override
    public void deleteEmployee(int id) {

    }

    @Override
    public void updateEmployee(Employee employee) {

    }

}
