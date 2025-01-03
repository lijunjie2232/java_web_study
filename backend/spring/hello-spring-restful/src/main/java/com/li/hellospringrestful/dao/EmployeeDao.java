package com.li.hellospringrestful.dao;

import com.li.hellospringrestful.bean.Employee;

public interface EmployeeDao {
    public Employee getEmployeeById(int id);
    public void addEmployee(Employee employee);
    public void deleteEmployee(int id);
    public void updateEmployee(Employee employee);
}
