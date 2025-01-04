package com.li.hellospringrestful.dao.impl;

import com.li.hellospringrestful.bean.Employee;
import com.li.hellospringrestful.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

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
        String sql = "INSERT INTO `employee` (`name`, `age`, `email`, `gender`, `address`, `salary`) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getAge(), employee.getEmail(), employee.getGender(), employee.getAddress(), employee.getSalary());
    }

    @Override
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM `employee` WHERE `id`=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE `employee` SET `name`=?, `age`=?, `email`=?, `gender`=?, `address`=?, `salary`=? WHERE `id`=?";
        jdbcTemplate.update(sql, employee.getName(), employee.getAge(), employee.getEmail(), employee.getGender(), employee.getAddress(), employee.getSalary(), employee.getId());
    }

}
