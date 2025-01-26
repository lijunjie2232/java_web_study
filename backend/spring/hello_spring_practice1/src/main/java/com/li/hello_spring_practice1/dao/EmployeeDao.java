package com.li.hello_spring_practice1.dao;

import com.li.hello_spring_practice1.bean.Employee;

import java.util.List;

public interface EmployeeDao {
    public Employee getEmployeeById(int id);
    public void addEmployee(Employee employee);
    public void deleteEmployee(int id);
    public void updateEmployee(Employee employee);
    public List<Employee> getAllEmployees(int offset, int pageSize);
}
