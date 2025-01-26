package com.li.hellospringrestful.dao;

import com.li.hellospringrestful.bean.Employee;

import java.util.List;

public interface EmployeeDao {
    public Employee getEmployeeById(int id);
    public void addEmployee(Employee employee);
    public void deleteEmployee(int id);
    public void updateEmployee(Employee employee);
    public List<Employee> getAllEmployees(int offset, int pageSize);
}
