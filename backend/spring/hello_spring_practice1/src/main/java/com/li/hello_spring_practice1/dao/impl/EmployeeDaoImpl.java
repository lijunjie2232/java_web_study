package com.li.hello_spring_practice1.dao.impl;

import com.li.hello_spring_practice1.bean.Employee;
import com.li.hello_spring_practice1.dao.EmployeeDao;
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

    @Override
    public List<Employee> getAllEmployees(int offset, int pageSize) {
        String sql = "SELECT * FROM `employee` LIMIT ?, ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Employee>(Employee.class), offset, pageSize);
    }

}
