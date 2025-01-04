package com.li.hellospringrestful.service.impl;

import com.li.hellospringrestful.bean.Employee;
import com.li.hellospringrestful.dao.EmployeeDao;
import com.li.hellospringrestful.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;


    @Override
    public void addEmployee(Employee employee) {
        employeeDao.addEmployee(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeDao.deleteEmployee(id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeDao.updateEmployee(employee);
    }

    @Override
    public Employee getEmployee(int id) {
        return employeeDao.getEmployeeById(id);
    }
}
